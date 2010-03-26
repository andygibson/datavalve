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

package org.fluttercode.spigot.jsfdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Andy Gibson
 *
 */
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
