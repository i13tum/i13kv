package de.tum.i13.client;

import java.io.IOException;

/**
 * Created by chris on 19.10.15.
 */
public class TestClient {
    private static final String TABLE_MYGRADES = "mygrades";
    private static final String KEY_MIDDLEWARE = "middleware";

    public static void main(String[] args) throws IOException {
        KVConnectionBuilder kvcb = new KVConnectionBuilder("localhost", 5559);

        try (ActiveConnection activeConnection = kvcb.connect()) {
            KVTable<AssessmentInformation> kvc = new KVTable<AssessmentInformation>(activeConnection, TABLE_MYGRADES,
                    AssessmentInformation.class);

            AssessmentInformation to = new AssessmentInformation(1.3, 92, 19, 13);

            System.out.print("PUT: Storing assignment information... ");
            if (kvc.put(KEY_MIDDLEWARE, to)) {
                System.out.println("Success!");
            } else {
                System.out.println("Error!");
            }

            System.out.print("GET: Retrieving assignment information... ");
            if (to.equals(kvc.get(KEY_MIDDLEWARE))) {
                System.out.println("Success!");
            } else {
                System.out.println("Error!");
            }

            System.out.print("DELETE: Removing assignment information... ");
            if (kvc.delete(KEY_MIDDLEWARE)) {
                System.out.println("Success!");
            } else {
                System.out.println("Error!");
            }

            System.out.print("EXISTS: Checking if assignment information are still stored... ");
            if (!kvc.exists(KEY_MIDDLEWARE)) {
                System.out.println("Success!");
            } else {
                System.out.println("Error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
