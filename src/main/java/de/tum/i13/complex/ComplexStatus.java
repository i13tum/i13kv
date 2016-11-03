package de.tum.i13.complex;

public class ComplexStatus {
	public enum StatusType {
	    OK, MSG_INCOMPLETE, MSG_FORMAT_UNKNOWN, UNKNOWN_OPERATOR, C1_NOTCOMPLEX, C2_NOTCOMPLEX, SERVER_ERROR
	}
	
	private StatusType type;
	
	public ComplexStatus(StatusType type){
		this.type = type;
	}

	public String marshal(){
		switch(type){
			case OK:
				return "ok";
			case MSG_INCOMPLETE:
				return "msgIncomplete";
			case MSG_FORMAT_UNKNOWN:
				return "msgFormatUnknown";
			case UNKNOWN_OPERATOR:
				return "unknownOperator";
			case C1_NOTCOMPLEX:
				return "c1NotAComplexNumber";
			case C2_NOTCOMPLEX:
				return "c2NotAComplexNumber";
			case SERVER_ERROR:
				return "serverError";
			default: // Invalid operator type
				return null;
		}
	}
	
	public static ComplexStatus unmarshal(String cs){
		switch(cs){
			case "ok":
				return new ComplexStatus(StatusType.OK);
			case "msgIncomplete":
				return new ComplexStatus(StatusType.MSG_INCOMPLETE);
			case "msgFormatUnknown":
				return new ComplexStatus(StatusType.MSG_FORMAT_UNKNOWN);
			case "unknownOperator":
				return new ComplexStatus(StatusType.UNKNOWN_OPERATOR);
			case "c1NotAComplexNumber":
				return new ComplexStatus(StatusType.C1_NOTCOMPLEX);
			case "c2NotAComplexNumber":
				return new ComplexStatus(StatusType.C2_NOTCOMPLEX);
			case "serverError":
				return new ComplexStatus(StatusType.SERVER_ERROR);
			default:  
				throw new IllegalArgumentException("Not a complex status: " + cs); // Not a valid string;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ComplexStatus other = (ComplexStatus) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
