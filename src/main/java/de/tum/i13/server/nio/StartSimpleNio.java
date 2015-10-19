package de.tum.i13.server.nio;

import java.io.IOException;

/**
 * Created by chris on 14.10.14.
 */
public class StartSimpleNio {

    public static void main(String[] args) throws IOException {
        SimpleNio sn = new SimpleNio();

        Integer port = 5559;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        sn.bindSockets("127.0.0.1", port);
        sn.start();
    }
}
