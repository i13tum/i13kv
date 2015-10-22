package de.tum.i13.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by chris on 19.10.15.
 */
public class KVConnectionBuilder {

    private final String host;
    private final int port;

    public KVConnectionBuilder(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ActiveConnection connect() throws IOException {
        Socket s = new Socket(this.host, this.port);

        PrintWriter output = new PrintWriter(s.getOutputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

        return new ActiveConnection(s, output, input);
    }
}
