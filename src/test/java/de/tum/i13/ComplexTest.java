package de.tum.i13;

import static org.hamcrest.CoreMatchers.is;
import de.tum.i13.complex.*;
import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

	private final double N_A = 1.0, N_B = 2.0;
	private final double M_A = 3.0, M_B = 3.0;
	private static final ComplexOperator.OperatorType OPERATOR = ComplexOperator.OperatorType.ADD;
	private static final ComplexStatus.StatusType STATUS = ComplexStatus.StatusType.OK;
	private final String COMPLEX_N = "(1.0,2.0)";
	private final String COMPLEX_M = "(3.0,3.0)";
	private final String COMPLEX_R = "<(1.0,2.0);(3.0,3.0);add>";
	private final String COMPLEX_S = "<(1.0,2.0);ok>";
	
	
	@Test
	public void marshalTest() {
		Complex n = new Complex(N_A,N_B);
		Assert.assertThat(n.marshal(), is(COMPLEX_N));
	}
	
	@Test
	public void unmarshalTest() {
		Complex n = new Complex(N_A,N_B);
		Assert.assertThat(Complex.unmarshal(COMPLEX_N), is(n));
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
		Complex n = new Complex(N_A,N_B);
		Assert.assertThat(Complex.unmarshal(COMPLEX_N), is(Complex.unmarshal(n.marshal())));
	}
	
	@Test
	public void requestMarshalTest() {
		Complex n = new Complex(N_A,N_B);
		Complex m = new Complex(M_A,M_B);
		ComplexOperator o = new ComplexOperator(OPERATOR);
		ComplexRequest r = new ComplexRequest(n,m,o);
		Assert.assertThat(r.marshal(), is(COMPLEX_R));
	}
	
	@Test
	public void requestUnmarshalTest() {
		Complex n = new Complex(N_A,N_B);
		Complex m = new Complex(M_A,M_B);
		ComplexOperator o = new ComplexOperator(OPERATOR);
		ComplexRequest r = new ComplexRequest(n,m,o);
		Assert.assertThat(ComplexRequest.unmarshal(COMPLEX_R), is(r));
	}
	
	public void requestMarshalUnmarshalTest(){
		Complex n = new Complex(N_A,N_B);
		Complex m = new Complex(M_A,M_B);
		ComplexOperator o = new ComplexOperator(OPERATOR);
		ComplexRequest r = new ComplexRequest(n,m,o);
		Assert.assertThat(r.marshal(), is(ComplexRequest.unmarshal(COMPLEX_R).marshal()));
	}	
	
	@Test
	public void responseMarshalTest() {
		Complex n = new Complex(N_A,N_B);
		ComplexStatus st = new ComplexStatus(STATUS);
		ComplexResponse s = new ComplexResponse(n,st);
		Assert.assertThat(s.marshal(), is(COMPLEX_S));
	}
	
	@Test
	public void responseUnmarshalTest() {
		Complex n = new Complex(N_A,N_B);
		ComplexStatus st = new ComplexStatus(STATUS);
		ComplexResponse s = new ComplexResponse(n,st);
		Assert.assertThat(ComplexResponse.unmarshal(COMPLEX_S), is(s));
	}
	
	public void responseMarshalUnmarshalTest(){
		Complex n = new Complex(N_A,N_B);
		ComplexStatus st = new ComplexStatus(STATUS);
		ComplexResponse s = new ComplexResponse(n,st);
		Assert.assertThat(s.marshal(), is(ComplexResponse.unmarshal(COMPLEX_S).marshal()));
	}	
}
