package de.tum.i13.threadpool;

import de.tum.i13.kv.KVStore;
import de.tum.i13.threadperconnection.ConnectionHandleThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by chris on 09.01.15.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket();
        final KVStore kv = new KVStore();
        Integer port = 5558;

        if(args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        //bind the socketserver only to localhost
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

        ExecutorService es = Executors.newFixedThreadPool(4);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            es.submit(new ConnectionHandlerRunnable(kv, clientSocket));
        }
    }
}
