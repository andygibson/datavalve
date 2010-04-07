/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Simple class that stores a list of Parameter instances
 * 
 * @author Andy Gibson
 * 
 */
public final class ParameterValues implements Iterable<Parameter>, Serializable {

	private static final long serialVersionUID = 1L;

	private List<Parameter> values = new ArrayList<Parameter>();

	public void add(Parameter parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException(
					"Cannot pass a null parameter into the parameter list");
		}
		values.add(parameter);
	}

	public Parameter add(String name, Object value) {
		Parameter parameter = new Parameter(name, value);
		add(parameter);
		return parameter;
	}

	public void clear() {
		values.clear();
	}

	public int size() {
		return values.size();
	}

	public Iterator<Parameter> iterator() {
		return values.iterator();
	}

	public boolean hasNullParameters() {
		for (Parameter parameter : values) {
			if (parameter.isNull()) {
				return true;
			}
		}
		return false;
	}

	public Parameter getParameter(int index) {
		return values.get(index);
	}

	public Parameter getParameter(String name) {
		if (name != null) {
			for (Parameter parameter : values) {
				if (name.equals(parameter.getName())) {
					return parameter;
				}
			}
		}
		return null;
	}	
}
