package org.jdataset.text;

import java.io.Serializable;

import org.jdataset.testing.TestDataFactory;

public class PhoneEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName;
	private String lastName;
	private String phone;

	public PhoneEntry(int id) {
		this(id, TestDataFactory.getFirstName(), TestDataFactory.getLastName(),
				TestDataFactory.getNumberText(10));
	}

	public PhoneEntry(int id, String firstName, String lastName, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
