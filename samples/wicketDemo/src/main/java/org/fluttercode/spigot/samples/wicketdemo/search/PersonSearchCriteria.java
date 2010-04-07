/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.wicketdemo.search;

import java.io.Serializable;

/**
 * @author Andy Gibson
 * 
 */
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
