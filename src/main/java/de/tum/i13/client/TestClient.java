package de.tum.i13.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by chris on 19.10.15.
 */
public class TestClient {

    public static void main(String[] args) throws IOException {
        KVConnectionBuilder kvcb = new KVConnectionBuilder("localhost", 5559);

        try(ActiveConnection activeConnection = kvcb.connect()) {

            KVClient kvc = new KVClient(activeConnection);
            TestObject to = new TestObject(1.3, 92, 19, 13);
            if(kvc.put("mygrades", "middleware", to)); {
                System.out.println("successfully stored");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
