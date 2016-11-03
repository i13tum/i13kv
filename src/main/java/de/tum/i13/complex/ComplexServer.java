package de.tum.i13.complex;

public class ComplexServer {	

	public ComplexServer(){
		
	}
	
	public String processRPC(String request){
		boolean valid = true;
		
		ComplexRequest complexRequest = null;
		ComplexResponse response = null;
		
		if(!request.matches("<.*;.*;.*>\r\n")){
			valid = false;
			response = new ComplexResponse(new Complex(0.0,0.0), new ComplexStatus(ComplexStatus.StatusType.MSG_FORMAT_UNKNOWN));
		}
		
		if(valid)
			try {
				complexRequest = ComplexRequest.unmarshal(request);
			} catch (ComplexRequestIncomplete e) {
				valid = false;
				response = new ComplexResponse(new Complex(0.0,0.0),new ComplexStatus(ComplexStatus.StatusType.MSG_INCOMPLETE));
			} catch (NotAComplexNumberException e) {
				valid = false;
				
				if(e.getPosition() == 1){
					response = new ComplexResponse(new Complex(0.0,0.0), new ComplexStatus(ComplexStatus.StatusType.C1_NOTCOMPLEX));
				} else {
					response = new ComplexResponse(new Complex(0.0,0.0), new ComplexStatus(ComplexStatus.StatusType.C2_NOTCOMPLEX));
				}
			} catch (NotAValidOperatorException e) {
				valid = false;
				
				response = new ComplexResponse(new Complex(0.0,0.0), new ComplexStatus(ComplexStatus.StatusType.UNKNOWN_OPERATOR));
			}
		
		if(valid)
			response = callLocalService(complexRequest.getC1(),complexRequest.getC2(),complexRequest.getOp());
		
		return response.marshal();
	}
	
	private ComplexResponse callLocalService(Complex c1, Complex c2, ComplexOperator op){		
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
				return new ComplexResponse(new Complex(0.0,0.0),new ComplexStatus(ComplexStatus.StatusType.UNKNOWN_OPERATOR));
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