package de.tum.i13;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by chris on 08.01.15.
 */
public class IntegrationTestKV {

    public static Integer port = 5558;

    public String doRequest(Socket s, String req) throws IOException {
        PrintWriter output = new PrintWriter(s.getOutputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

        output.write(req + "\r\n");
        output.flush();

        String res = input.readLine();
        return res;
    }

    public String doRequest(String req) throws IOException {
        Socket s = new Socket();
        s.connect(new InetSocketAddress("127.0.0.1", port));
        String res = doRequest(s, req);
        s.close();

        return res;
    }

    @Test
    public void doing_a_few_requests() throws IOException, InterruptedException {
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    de.tum.i13.server.nio.StartSimpleNioServer.main(new String[]{port.toString()});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start(); // started the server
        Thread.sleep(2000);

        for (int tcnt = 0; tcnt < 50; tcnt++){
            final int finalTcnt = tcnt;
            new Thread(){
                @Override
                public void run() {
                    try {
                        Socket s = new Socket("127.0.0.1", port);
                        System.out.println("#startednewthread:" + finalTcnt + "#socket:" + s);
                        for(int i = 0; i < 100000; i++) {
                            Assert.assertThat(doRequest(s, "PUT table key"+ finalTcnt +" valuetest" + i), containsString("OK"));
                            Assert.assertThat(doRequest(s, "GET table key" + finalTcnt), containsString("valuetest" + i));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }



        //Assert.assertThat(doRequest("GET table key"), containsString("valuetest"));

        Thread.sleep(5000);
        th.interrupt();
    }

    @Test
    public void doin_a_few_request_with_always_new_connections() {
        for (int tcnt = 0; tcnt < 50; tcnt++){
            final int finalTcnt = tcnt;
            new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println("#startednewthread:" + finalTcnt);
                        for(int i = 0; i < 100000; i++) {
                            Assert.assertThat(doRequest("PUT table key"+ finalTcnt +" valuetest" + i), containsString("OK"));
                            Assert.assertThat(doRequest("GET table key" + finalTcnt), containsString("valuetest" + i));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
