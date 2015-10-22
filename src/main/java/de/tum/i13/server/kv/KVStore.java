package de.tum.i13.server.kv;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chris on 08.01.15.
 */
public class KVStore {
    final String middledelimiter = "#~#";
    ConcurrentHashMap<String, String> store;

    public KVStore() {
        this.store = new ConcurrentHashMap<String, String>();
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

                    String res = store.getOrDefault(table + middledelimiter + key, "ERROR");

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

                    store.put(table + middledelimiter + key, value);
                    System.out.println(String.format("#PUT:%s%s-%s", table, key, value));

                    return "OK\r\n";
                } catch (NoSuchElementException ex) {
                    return "ERROR\r\n";
                }
            } else if (command.equalsIgnoreCase("DELETE")) { // deletes the
                                                             // key-value
                                                             // pair
                String table = st.nextToken();
                String key = st.nextToken();

                String res = store.remove(table + middledelimiter + key);
                System.out.println(String.format("#DELETE:%s%s-%s", table, key, res));

                return (res != null ? "DELETED" : "NOT_FOUND") + "\r\n";

            } else if (command.equalsIgnoreCase("EXISTS")) { // checks if the
                                                             // key exists
                    String table = st.nextToken();
                    String key = st.nextToken();

                    boolean res = store.containsKey(table + middledelimiter + key);
                    System.out.println(String.format("#EXISTS:%s%s-%s", table, key, res));

                    if(res) {
                        return "EXISTS\r\n";
                    }
                    else {
                        return "NOT_FOUND\r\n";
                    }

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
