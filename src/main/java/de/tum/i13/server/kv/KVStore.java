package de.tum.i13.server.kv;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chris on 08.01.15.
 */
public class KVStore {

    final String middledelimiter = "#~#";
    public final String TELNET_ENCODING = "ISO-8859-1"; //encoding of telenet
    ConcurrentHashMap<String, String> store;


    public KVStore() {
        this.store = new ConcurrentHashMap<String, String>();
    }

    public String process(String firstLine) {
        try{
            StringTokenizer st = new StringTokenizer(firstLine, " \r\n");
            String command = st.nextToken();

            if (command.equalsIgnoreCase("GET")) {
                try {
                    String table = st.nextToken();
                    String key = st.nextToken();

                    String res = store.getOrDefault(table + middledelimiter + key, "ERROR");

                    System.out.println(String.format("#GET:%s%s-%s", table, key, res));
                    return res + "\r\n";
                }catch (NoSuchElementException ex) {
                    return "ERROR\r\n";
                }

            } else if (command.equalsIgnoreCase("PUT")) {
                try {
                    String table = st.nextToken();
                    String key = st.nextToken();
                    String value = st.nextToken();

                    store.put(table + middledelimiter + key, value);
                    System.out.println(String.format("#PUT:%s%s-%s", table, key, value));

                    return "OK\r\n";
                } catch (NoSuchElementException ex) {
                    return "ERROR\r\n";
                }
            }
            else {
                return "DID_NOT_UNDERSTAND\r\n";
            }
        }
        catch (Exception ex) {
            System.out.println("Exception occured:");
            ex.printStackTrace();

            return "ERROR\r\n";
        }
    }
}
