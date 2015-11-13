package de.tum.i13.server.kv;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chris on 08.01.15.
 */
public class KVStore {
    final String middledelimiter = "#~#";
    private final BackingMap bm;

    public KVStore(BackingMap bm) {
        this.bm = bm;
    }

    public KVStore() {
        this.bm = new BackingMap();
    }

    public String process(String firstLine) {
        try {
            StringTokenizer st = new StringTokenizer(firstLine, " \r\n");
            String command = st.nextToken();

            if (command.equalsIgnoreCase("GET")) { // retrieves the stored value
                                                   // for a given key
                try {
                    String table = st.nextToken();
                    String key = st.nextToken();

                    String res = bm.atomic((kv) -> kv.getOrDefault(table + middledelimiter + key, "ERROR"));

                    System.out.println(String.format("#GET:%s%s-%s", table, key, res));
                    return res + "\r\n";
                } catch (NoSuchElementException ex) {
                    return "ERROR\r\n";
                }

            } else if (command.equalsIgnoreCase("PUT")) { // updates the stored
                                                          // value for a given
                                                          // key
                try {
                    String table = st.nextToken();
                    String key = st.nextToken();
                    String value = st.nextToken();

                    String res = bm.atomic((kv) -> {
                        kv.put(table + middledelimiter + key, value);
                        return "OK\r\n";
                    });

                    System.out.println(String.format("#PUT:%s%s-%s", table, key, value));

                    return res;
                } catch (NoSuchElementException ex) {
                    return "ERROR\r\n";
                }
            } else if (command.equalsIgnoreCase("DELETE")) { // deletes the
                                                             // key-value
                                                             // pair
                String table = st.nextToken();
                String key = st.nextToken();

                String res = bm.atomic((kv) -> kv.remove(table + middledelimiter + key));

                System.out.println(String.format("#DELETE:%s%s-%s", table, key, res));

                return (res != null ? "DELETED" : "NOT_FOUND") + "\r\n";

            } else if (command.equalsIgnoreCase("EXISTS")) { // checks if the
                // key exists
                String table = st.nextToken();
                String key = st.nextToken();

                String res = bm.atomic((kv) -> {
                    if (kv.containsKey(table + middledelimiter + key)) {
                        return "EXISTS\r\n";
                    } else {
                        return "NOT_FOUND\r\n";
                    }
                });
                System.out.println(String.format("#%s:%s%s-%s", res.replace("\r\n", ""), table, key, res));
                return res;
            } else {
                return "DID_NOT_UNDERSTAND\r\n";
            }
        } catch (Exception ex) {
            System.out.println("Exception occured:");
            ex.printStackTrace();

            return "ERROR\r\n";
        }
    }
}
