/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.sample.wicket.search;

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
