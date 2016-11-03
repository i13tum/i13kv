package de.tum.i13.complex;

public class ComplexServer {

	public ComplexServer(){
		
	}
	
	public ComplexResponse callLocalService(Complex c1, Complex c2, ComplexOperator op){		
		switch(op.getType()){
			case ADD:
				return add(c1,c2);
			case SUBSTRACT:
				return substract(c1,c2);
			case MULTIPLY:
				return multiply(c1,c2);
			case DIVIDE:
				return divide(c1,c2);
			default:
				return new ComplexResponse(c1,new ComplexStatus(ComplexStatus.StatusType.UNKNOWN_OPERATOR));
		}
	}

	private ComplexResponse add(Complex c1, Complex c2) {
		return new ComplexResponse(c1.add(c2),new ComplexStatus(ComplexStatus.StatusType.OK));
	}

	private ComplexResponse substract(Complex c1, Complex c2) {
		return new ComplexResponse(c1.substract(c2),new ComplexStatus(ComplexStatus.StatusType.OK));
	}

	private ComplexResponse multiply(Complex c1, Complex c2) {
		return new ComplexResponse(c1.multiply(c2),new ComplexStatus(ComplexStatus.StatusType.OK));
	}

	private ComplexResponse divide(Complex c1, Complex c2) {
		return new ComplexResponse(c1.divide(c2),new ComplexStatus(ComplexStatus.StatusType.OK));
	}
}
