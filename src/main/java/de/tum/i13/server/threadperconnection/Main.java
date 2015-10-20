package de.tum.i13.server.threadperconnection;

import de.tum.i13.server.kv.KVStore;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by chris on 09.01.15.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        final KVStore kv = new KVStore();
        Integer port = 5558;

        if(args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        //bind the socketserver only to localhost
        final ServerSocket serverSocket = new ServerSocket();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Closing thread per connection kv server");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //bind to localhost only
        serverSocket.bind(new InetSocketAddress("127.0.0.1", port));

        while (true) {
            Socket clientSocket = serverSocket.accept();

            //When we accept a connection, we start a new Thread for this connection
            Thread th = new ConnectionHandleThread(kv, clientSocket);
            th.start();
        }
    }
}
