package de.tum.i13.complex;

import java.util.StringTokenizer;

public class ComplexRequest {
	private Complex c1, c2;
	private ComplexOperator op;
	
	public ComplexRequest(Complex c1, Complex c2, ComplexOperator op){
		this.c1 = c1;
		this.c2 = c2;
		this.op = op;
	}
	
	public String marshal(){
		return "<" + c1.marshal() + ";" + c2.marshal() + ";" + op.marshal() + ">";
	}
	
	public static ComplexRequest unmarshal(String m1s) throws ComplexRequestIncomplete, NotAComplexNumberException, NotAValidOperatorException {
		StringTokenizer tokens = new StringTokenizer(m1s,"<;>");
		
		Complex c1, c2;
		ComplexOperator op;
		
		String c1s = null, c2s = null, ops = null;
		
		if(tokens.countTokens() < 3)
			throw new ComplexRequestIncomplete(m1s);
		
		try {
			c1s = tokens.nextToken();
			c1 = Complex.unmarshal(c1s);
		} catch (Exception e){
			throw new NotAComplexNumberException(c1s,1);
		}
		
		try {
			c2s = tokens.nextToken();
			c2 = Complex.unmarshal(c2s);
		} catch (Exception e){
			throw new NotAComplexNumberException(c2s,2);
		}
		
		try {
			ops = tokens.nextToken();
			op = ComplexOperator.unmarshal(ops);
		} catch (Exception e){
			throw new NotAValidOperatorException(ops);
		}
		
		return new ComplexRequest(c1,c2,op);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexRequest other = (ComplexRequest) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		if (op == null) {
			if (other.op != null)
				return false;
		} else if (!op.equals(other.op))
			return false;
		return true;
	}

	public Complex getC1() {
		return c1;
	}

	public Complex getC2() {
		return c2;
	}

	public ComplexOperator getOp() {
		return op;
	}
}
