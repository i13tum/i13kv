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
		
		return new Complex(Double.parseDouble(tokens.nextToken()),Double.parseDouble(tokens.nextToken()));
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
}
