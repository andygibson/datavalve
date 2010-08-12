/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.params;

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
