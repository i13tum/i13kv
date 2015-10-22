package de.tum.i13.server.nio;

import de.tum.i13.server.kv.KVStore;
import de.tum.i13.shared.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.List;

/**
 * Based on http://rox-xmlrpc.sourceforge.net/niotut/
 */
public class SimpleNioServer {

	private KVStore kv = new KVStore();

	private List<ChangeRequest> pendingChanges;
	private Map<SelectionKey, List<ByteBuffer>> pendingWrites;
	private Map<SelectionKey, byte[]> pendingReads;

	private Selector selector;
	private ServerSocketChannel serverChannel;

	private ByteBuffer readBuffer;

	public SimpleNioServer() {
		this.pendingChanges = new LinkedList<ChangeRequest>();
		this.pendingWrites = new HashMap<SelectionKey, List<ByteBuffer>>();
		this.pendingReads = new HashMap<SelectionKey, byte[]>();

		this.readBuffer = ByteBuffer.allocate(8192); // = 2^13
	}

	public void bindSockets(String servername, int port) throws IOException {
		// Create a new non-blocking server selectionKey channel
		this.serverChannel = ServerSocketChannel.open();
		this.serverChannel.configureBlocking(false);

		// Bind the server selectionKey to the specified address and port
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(servername), port);
		this.serverChannel.socket().bind(isa);

		// Register the server selectionKey channel, indicating an interest in
		// accepting new connections
		this.selector = SelectorProvider.provider().openSelector();
		this.serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void start() throws IOException {
		while (true) {
			// Process queued interest changes
			for (ChangeRequest change : this.pendingChanges) {
				change.selectionKey.interestOps(change.ops);
			}
			this.pendingChanges.clear();

			// Wait for an event one of the registered channels
			this.selector.select();

			// Iterate over the set of keys for which events are available
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
			while (selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				selectedKeys.remove();

				if (!key.isValid()) {
					continue;
				}

				// Check what event is available and deal with it
				if (key.isAcceptable()) {
					accept(key);
				} else if (key.isReadable()) {
					read(key);
				} else if (key.isWritable()) {
					write(key);
				}
			}
		}
	}

	private void accept(SelectionKey key) throws IOException {
		// For an accept to be pending the channel must be a server selectionKey
		// channel.
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

		// Accept the connection and make it non-blocking
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);

		// Register the new SocketChannel with our Selector, indicating
		// we'd like to be notified when there's data waiting to be read
		socketChannel.register(this.selector, SelectionKey.OP_READ);
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		// Clear out our read buffer so it's ready for new data
		this.readBuffer.clear();

		// Attempt to read off the channel
		int numRead;
		try {
			numRead = socketChannel.read(this.readBuffer);
		} catch (IOException e) {
			// The remote forcibly closed the connection, cancel
			// the selection key and close the channel.
			key.cancel();
			socketChannel.close();
			return;
		}

		if (numRead == -1) {
			// Remote entity shut the selectionKey down cleanly. Do the
			// same from our end and cancel the channel.
			key.channel().close();
			key.cancel();
			return;
		}

		byte[] dataCopy = new byte[numRead];
		System.arraycopy(this.readBuffer.array(), 0, dataCopy, 0, numRead);
		System.out.println("#tempdata:" + new String(dataCopy, Constants.TELNET_ENCODING));

		// If we have already received some data, we add this to our buffer
		if (this.pendingReads.containsKey(key)) {
			byte[] existingBytes = pendingReads.get(key);

			byte[] concatenated = new byte[existingBytes.length + dataCopy.length];
			System.arraycopy(existingBytes, 0, concatenated, 0, existingBytes.length);
			System.arraycopy(dataCopy, 0, concatenated, existingBytes.length, dataCopy.length);

			// In case we have now
			if (checkIfFinished(concatenated)) {
				String data = new String(concatenated, Constants.TELNET_ENCODING);
				this.pendingReads.remove(key);
				handleRequest(key, data);
			} else {
				this.pendingReads.put(key, concatenated);
			}
		} else {
			// In case we got already the whole request within one step we don't
			// have to wait again
			// In this case no buffering in the hashtable and start direct
			// handling the request
			if (checkIfFinished(dataCopy)) {
				String data = new String(dataCopy, Constants.TELNET_ENCODING);
				handleRequest(key, data);
			} else {
				// in case it is the first request we
				if (this.pendingReads.containsKey(key)) {
					byte[] existingBytes = this.pendingReads.get(key);
					byte[] concatenated = new byte[existingBytes.length + dataCopy.length];
					System.arraycopy(existingBytes, 0, concatenated, 0, existingBytes.length);
					System.arraycopy(dataCopy, 0, concatenated, existingBytes.length, dataCopy.length);

					this.pendingReads.put(key, concatenated);
				} else {
					this.pendingReads.put(key, dataCopy);
				}
			}
		}
	}

	private void write(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		List<ByteBuffer> queue = this.pendingWrites.get(key);

		// Write until there's no more data left ...
		while (!queue.isEmpty()) {
			ByteBuffer buf = (ByteBuffer) queue.get(0);
			socketChannel.write(buf);
			if (buf.remaining() > 0) {
				// ... or the selectionKey's buffer fills up
				break;
			}
			queue.remove(0);
		}

		if (queue.isEmpty()) {
			// We wrote away all data, so we're no longer interested
			// in writing on this selectionKey. Switch back to waiting for
			// data.
			key.interestOps(SelectionKey.OP_READ);
		}
	}

	// This is telnet specific, maybe you have to change it according to your
	// protocol
	private boolean checkIfFinished(byte[] data) {
		int length = data.length;
		if (length < 3) {
			return false;
		} else {
			if (data[length - 1] == '\n') {
				if (data[length - 2] == '\r') {
					return true;
				}
			}
			return false;
		}
	}

	private void handleRequest(SelectionKey selectionKey, String request) {
		try {
			String res = kv.process(request);
			send(selectionKey, res.getBytes(Constants.TELNET_ENCODING));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void send(SelectionKey selectionKey, byte[] data) {
		// Indicate we want the interest ops set changed
		this.pendingChanges.add(new ChangeRequest(selectionKey, SelectionKey.OP_WRITE));

		// And queue the data we want written
		List<ByteBuffer> queue = this.pendingWrites.get(selectionKey);
		if (queue == null) {
			queue = new ArrayList<ByteBuffer>();
			this.pendingWrites.put(selectionKey, queue);
		}
		queue.add(ByteBuffer.wrap(data));

		// Finally, wake up our selecting thread so it can make the required
		// changes
		this.selector.wakeup();
	}
}
