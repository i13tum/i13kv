package de.tum.i13.complex;

public class ComplexRequestIncomplete extends Exception {

	private String error;
	
	public ComplexRequestIncomplete(String m1s) {
		this.error = m1s;
	}

}
