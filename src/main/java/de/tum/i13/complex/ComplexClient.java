package de.tum.i13.complex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tum.i13.shared.Constants;
import de.tum.i13.client.ActiveConnection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by kaiwen on 3.11.16.
 */
public class ComplexClient {
    private final ActiveConnection activeConnection;
    private final Gson gs;

    public ComplexClient(ActiveConnection activeConnection) {
        this.activeConnection = activeConnection;
        this.gs = new GsonBuilder().setPrettyPrinting().create();
    }

    private String encodeBase64(String toConvert) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(toConvert.getBytes(Constants.TELNET_ENCODING));
    }

    private String decodeBase64(String toConvert) throws UnsupportedEncodingException {
        byte[] decode = Base64.getDecoder().decode(toConvert);
        return new String(decode, Constants.TELNET_ENCODING);
    }

    private String doRPC(String command) throws IOException {
        String jsonStr = gs.toJson(command);
        String enc_value = encodeBase64(jsonStr);

        this.activeConnection.write(command + "\r\n");
        String result = decodeBase64(this.activeConnection.readline());

        return result;
    }

    public Complex callRemoteService(Complex c1, Complex c2, ComplexOperator op) {
		ComplexRequest request = new ComplexRequest(c1, c2, op);
		
		try {
			String result = doRPC(request.marshal());
			return Complex.unmarshal(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
    }
}
