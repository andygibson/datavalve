package org.jdataset.samples.examples;

import java.io.Serializable;

public class Person implements Serializable{

	private Long id;
	private String firstName;
	private String lastName;

	public Person(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {	
		//return super.toString() + String.format("%s [%d,%s %s]", super.toString(),id,firstName,lastName);
		return String.format("Person [%d,%s %s]", id,firstName,lastName);
	}

	public String getDisplayName() {
		return firstName + " " + lastName;
	}
	
	public String getName() {
		return lastName + ", "+firstName;
	}
}
