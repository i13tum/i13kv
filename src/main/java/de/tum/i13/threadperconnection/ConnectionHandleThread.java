package de.tum.i13.threadperconnection;

import de.tum.i13.kv.KVStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by chris on 09.01.15.
 */
public class ConnectionHandleThread extends Thread {
    private KVStore kv;
    private Socket clientSocket;

    public ConnectionHandleThread(KVStore store, Socket clientSocket) {
        this.kv = store;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), kv.TELNET_ENCODING));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), kv.TELNET_ENCODING));

            String firstLine;
            while ((firstLine = in.readLine()) != null) {
                String res = kv.process(firstLine);
                out.write(res);
                out.flush();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
