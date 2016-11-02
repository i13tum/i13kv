package de.tum.i13;

import de.tum.i13.complex.Complex;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class ComplexTest {

	private final double N_A = 1.0, N_B = 2.0;
	private final String COMPLEX_N = "(1.0,2.0)";
	
	@Test
	public void marshalTest() {
		Complex n = new Complex(N_A,N_B);
		Assert.assertThat(n.marshal(), is(COMPLEX_N));
	}

	@Test
	public void marshalUnmarshalMarshalTest() {
		Complex n = new Complex(N_A,N_B);
		Assert.assertThat(n.marshal(), is(Complex.unmarshal(n.marshal()).marshal()));
	}
	
	@Test
	public void marshalUnmarshalTest() {
		Complex n = new Complex(N_A,N_B);
		Assert.assertThat(n.marshal(), is(Complex.unmarshal(COMPLEX_N).marshal()));
	}

	@Test
	public void unmarshalMarshalUnmarshalTest() {
		Assert.assertThat(Complex.unmarshal(COMPLEX_N), is(Complex.unmarshal(new Complex(N_A,N_B).marshal())));
	}
	
	@Test
	public void unmarshalMarshalTest() {
		Assert.assertThat(Complex.unmarshal(COMPLEX_N), is(new Complex(N_A,N_B)));
	}

}