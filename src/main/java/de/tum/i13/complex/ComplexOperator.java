package de.tum.i13.complex;

public class ComplexOperator {
	public enum OperatorType {
	    ADD, SUBSTRACT, MULTIPLY, DIVIDE
	}
	
	private OperatorType type;
	
	public ComplexOperator(OperatorType type){
		this.type = type;
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
		ComplexOperator other = (ComplexOperator) obj;
		if (type != other.type)
			return false;
		return true;
	}

	public String marshal(){
		switch(type){
			case ADD:
				return "add";
			case SUBSTRACT:
				return "substract";
			case MULTIPLY:
				return "multiply";
			case DIVIDE:
				return "divide";
			default: // Invalid operator type
				return null;
		}
	}
	
	public static ComplexOperator unmarshal(String cos){
		switch(cos){
			case "add":
				return new ComplexOperator(OperatorType.ADD);
			case "substract":
				return new ComplexOperator(OperatorType.SUBSTRACT);
			case "multiply":
				return new ComplexOperator(OperatorType.MULTIPLY);
			case "divide":
				return new ComplexOperator(OperatorType.DIVIDE);
			default:  
				throw new IllegalArgumentException("Not a complex operator: " + cos); // Not a valid string;
		}
	}

	public OperatorType getType() {
		return type;
	}
}
