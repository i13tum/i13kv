package de.tum.i13.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by chris on 19.10.15.
 */
public class ActiveConnection implements AutoCloseable{
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader input;

    public ActiveConnection(Socket socket, PrintWriter output, BufferedReader input) {
        this.socket = socket;

        this.output = output;
        this.input = input;
    }

    public void write(String command) {
        output.write(command);
        output.flush();
    }

    public String readline() throws IOException {
        return input.readLine();
    }

    public void close() throws Exception {
        output.close();
        input.close();
        socket.close();
    }
}
