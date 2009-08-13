package org.jdataset.sample.wicket.search;

import java.io.Serializable;

public class PersonSearchCriteria implements Serializable {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	protected String addWildcardToString(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		return value + "%";
	}
	
	public String getFirstNameValue() {
		return addWildcardToString(firstName);
	}
	
	public String getLastNameValue() {
		return addWildcardToString(lastName);
	}

	public String getPhoneValue() {
		return addWildcardToString(phone);
	}
	
}
