package de.tum.i13.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by chris on 19.10.15.
 */
public class KVClient {

    private final ActiveConnection activeConnection;
    private final Gson gs;

    public KVClient(ActiveConnection activeConnection) {

        this.activeConnection = activeConnection;
        this.gs = new GsonBuilder().setPrettyPrinting().create();
    }

    private String convertBase64(String toConvert) {
        return Base64.getEncoder().encodeToString(toConvert.getBytes());
    }


    public Boolean put(String table, String key, Object value) throws IOException {

        String jsonStr = gs.toJson(value);
        String enc_value = convertBase64(jsonStr);

        this.activeConnection.write("PUT " + table + " "  + key + " " + enc_value + "\r\n");
        String result = this.activeConnection.readline();

        if (result == "OK") {
            return true;
        }
        else {
            return false;
        }
    }
}
