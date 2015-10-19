package de.tum.i13.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by chris on 19.10.15.
 */
public class KVTable<T> {

    public final String TELNET_ENCODING = "ISO-8859-1"; //encoding of telenet

    private final ActiveConnection activeConnection;
    private final String table;
    private final Class<T> typeParameterClass;
    private final Gson gs;

    public KVTable(ActiveConnection activeConnection, String table, Class<T> typeParameterClass) {

        this.activeConnection = activeConnection;
        this.table = table;
        this.typeParameterClass = typeParameterClass;
        this.gs = new GsonBuilder().setPrettyPrinting().create();
    }

    private String encodeBase64(String toConvert) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(toConvert.getBytes(this.TELNET_ENCODING));
    }

    private String decodeBase64(String toConvert) throws UnsupportedEncodingException {
        byte[] decode = Base64.getDecoder().decode(toConvert);
        return new String(decode, this.TELNET_ENCODING);
    }


    public Boolean put(String key, T value) throws IOException {

        String jsonStr = gs.toJson(value);
        String enc_value = encodeBase64(jsonStr);

        this.activeConnection.write("PUT " + this.table + " "  + key + " " + enc_value + "\r\n");
        String result = this.activeConnection.readline();

        if (result == "OK") {
            return true;
        }
        else {
            return false;
        }
    }

    public T get(String key) throws IOException {

        this.activeConnection.write("GET " + this.table + " " + key + "\r\n");
        String read = decodeBase64(this.activeConnection.readline());
        return gs.fromJson(read, typeParameterClass);
    }
}
