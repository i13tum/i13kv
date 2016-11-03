package de.tum.i13.complex;

public class NotAValidOperatorException extends Exception {

	private String op;
	
	public NotAValidOperatorException(String ops) {
		this.op = ops;
	}

}
