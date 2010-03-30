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
