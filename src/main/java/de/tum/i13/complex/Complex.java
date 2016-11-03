package de.tum.i13.complex;

import java.util.StringTokenizer;

/**
 * Created by kzhang on 02.11.16.
 */
public class Complex {
	private double x, y;
	
	public Complex(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * A complex number is a + bi = (a,b)
	 * @return
	 */
	public String marshal() {
		return "(" + x + "," + y + ")";
	}
	
	/**
	 * Will throw exceptions if the string is invalid.
	 * @param cs
	 * @return
	 */
	public static Complex unmarshal(String cs){
		StringTokenizer tokens = new StringTokenizer(cs,"(,)");
		
		try {
			return new Complex(Double.parseDouble(tokens.nextToken()),Double.parseDouble(tokens.nextToken()));
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a complex number: " + cs);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public Complex add(Complex c2) {
		double x = this.x + c2.x;
		double y = this.y + c2.y;
		return new Complex(x,y);
	}

	public Complex substract(Complex c2) {
		double x = this.x - c2.x;
		double y = this.y - c2.y;
		return new Complex(x,y);
	}

	public Complex multiply(Complex c2) {
		double x = this.x * c2.x - y * c2.y;
		double y = this.x * c2.y + this.y * c2.x;
		return new Complex(x,y);
	}

	public Complex divide(Complex c2) {
	    double x = (this.x*c2.x+this.y*c2.y)/(c2.x*c2.x+c2.y*c2.y);
	    double y = (this.y*c2.x-this.x*c2.y)/(c2.x*c2.x+c2.y*c2.y);
		return new Complex(x,y);
	}
}
