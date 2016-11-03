package de.tum.i13.complex;

import java.util.StringTokenizer;

public class ComplexResponse {
	private Complex cr;
	private ComplexStatus st;

	public ComplexResponse(Complex cr, ComplexStatus st){
		this.cr = cr;
		this.st = st;
	}
	
	public String marshal(){
		return "<" + cr.marshal() + ";" + st.marshal() + ">";
	}
	
	public static ComplexResponse unmarshal(String m2s){
		StringTokenizer tokens = new StringTokenizer(m2s,"<;>");
		try {
			return new ComplexResponse(Complex.unmarshal(tokens.nextToken()),ComplexStatus.unmarshal(tokens.nextToken()));
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a complex response: " + m2s);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cr == null) ? 0 : cr.hashCode());
		result = prime * result + ((st == null) ? 0 : st.hashCode());
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
		ComplexResponse other = (ComplexResponse) obj;
		if (cr == null) {
			if (other.cr != null)
				return false;
		} else if (!cr.equals(other.cr))
			return false;
		if (st == null) {
			if (other.st != null)
				return false;
		} else if (!st.equals(other.st))
			return false;
		return true;
	}
}
