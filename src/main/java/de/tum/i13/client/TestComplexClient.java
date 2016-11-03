package de.tum.i13.client;

import de.tum.i13.complex.*;

import java.io.IOException;

/**
 * Created by kaiwen on 3.11.16.
 */
public class TestComplexClient {

    public static void main(String[] args) throws IOException {
        KVConnectionBuilder kvcb = new KVConnectionBuilder("localhost", 5559);

        try (ActiveConnection activeConnection = kvcb.connect()) {
            ComplexClient cc = new ComplexClient(activeConnection);

			Complex c1 = new Complex(2.0,3.0);
			Complex c2 = new Complex(3.0,5.0);
						
            System.out.print("(2+3i) + (3+5i)");
  			System.out.println(cc.callRemoteService(c1,c2,new ComplexOperator(ComplexOperator.OperatorType.ADD)));

            System.out.print("(2+3i) - (3+5i)");
  			System.out.println(cc.callRemoteService(c1,c2,new ComplexOperator(ComplexOperator.OperatorType.SUBSTRACT)));

            System.out.print("(2+3i) * (3+5i)");
  			System.out.println(cc.callRemoteService(c1,c2,new ComplexOperator(ComplexOperator.OperatorType.MULTIPLY)));

            System.out.print("(2+3i) / (3+5i)");
  			System.out.println(cc.callRemoteService(c1,c2,new ComplexOperator(ComplexOperator.OperatorType.DIVIDE)));			

  } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
