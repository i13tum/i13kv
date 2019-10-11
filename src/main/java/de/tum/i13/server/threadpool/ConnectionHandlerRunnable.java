package de.tum.i13.server.threadpool;

import de.tum.i13.server.kv.KVStore;
import de.tum.i13.shared.Constants;

import java.io.*;
import java.net.Socket;

/**
 * Created by chris on 09.01.15.
 */
public class ConnectionHandlerRunnable implements Runnable {

    private KVStore kv;
    private Socket clientSocket;

    public ConnectionHandlerRunnable(KVStore kv, Socket clientSocket) {
        this.kv = kv;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Constants.TELNET_ENCODING));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), Constants.TELNET_ENCODING));

            String firstLine;
            while ((firstLine = in.readLine()) != null) {
                String res = kv.process(firstLine);
                out.write(res);
                out.flush();
            }

            clientSocket.close(); //Here we close the socket

        } catch(Exception ex) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
    }
}
