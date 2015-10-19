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
            ArrayList<Double> gradeList = new ArrayList<>();
            gradeList.add(1.3);
            gradeList.add(2.3);

            if(kvc.put("mygrades", "middleware", gradeList)); {
                System.out.println("successfully stored");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
