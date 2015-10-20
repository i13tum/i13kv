package de.tum.i13.server.threadpool;

import de.tum.i13.server.kv.KVStore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Closing threadpool connection kv server");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //bind the socketserver only to localhost
        serverSocket.bind(new InetSocketAddress("127.0.0.1", port));

        //Start a threadpool with a fixed amount of threads
        ExecutorService es = Executors.newFixedThreadPool(4);

        while (true) {
            Socket clientSocket = serverSocket.accept();

            //Submit the connectionhandler to the threadpool
            //The connections are closed after connecting to the server, if not we could only support 4 requests at the same time
            es.submit(new ConnectionHandlerRunnable(kv, clientSocket));
        }
    }
}
