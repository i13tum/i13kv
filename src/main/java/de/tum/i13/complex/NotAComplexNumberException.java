package de.tum.i13.complex;

public class NotAComplexNumberException extends Exception {

	private String cs;
	private int position;
	
	public NotAComplexNumberException(String cs, int i) {
		this.cs = cs;
		this.position = i;
	}

	public String getCs() {
		return cs;
	}

	public int getPosition() {
		return position;
	}

}
