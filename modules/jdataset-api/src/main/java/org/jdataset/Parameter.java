package org.jdataset.params;

/**
 * A value holder used to store resolved parameters. 
 *  
 * @author Andy Gibson
 * 
 */
public final class Parameter {

	private Object value;
	private String name;

	public Parameter(String name) {
		this(name, null);
	}

	public Parameter(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public boolean isNull() {
		return value == null;
	}

	public boolean isNotNull() {
		return value != null;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString() + " name = '" + getName() + "' value = '"
				+ value + "'";
	}
}
