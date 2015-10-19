package de.tum.i13.client;

import java.io.IOException;

/**
 * Created by chris on 19.10.15.
 */
public class TestClient {

    public static void main(String[] args) throws IOException {
        KVConnectionBuilder kvcb = new KVConnectionBuilder("localhost", 5559);

        try(ActiveConnection activeConnection = kvcb.connect()) {

            KVTable<TestObject> kvc = new KVTable(activeConnection, "mygrades", TestObject.class);

            TestObject to = new TestObject(1.3, 92, 19, 13);
            if(kvc.put("middleware", to)); {
                System.out.println("successfully stored");
            }


            TestObject retrieve_again = kvc.get("middleware");
            if(to.equals(retrieve_again)) {
                System.out.println("retrieved the same again");
            };
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
