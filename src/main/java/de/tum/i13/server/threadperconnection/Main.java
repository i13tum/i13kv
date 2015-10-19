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
        serverSocket.bind(new InetSocketAddress("127.0.0.1", port));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Closing selectionKey");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread th = new ConnectionHandleThread(kv, clientSocket);
            th.start();
        }
    }
}
