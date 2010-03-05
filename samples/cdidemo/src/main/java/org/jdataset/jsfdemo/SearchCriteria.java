package org.jdataset.jsfdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named("searchCriteria")
@RequestScoped
public class SearchCriteria {

	private String firstName;
	private String lastName;
	private String phone;

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

	protected String addWildcard(String value) {
		if (value == null) {return null;}
		value = value.trim().toUpperCase();
		if (value.length() == 0) {
			return null;
		}
		return value+"%";
	}
	public String getFirstNameWildcard() {
		return addWildcard(firstName);
	}
	
	
	public String getLastNameWildcard() {
		return addWildcard(lastName);
	}
	
	public String getPhoneWildcard() {		
		return addWildcard(phone);
	}
	
}
