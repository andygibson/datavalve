package org.jdataset.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdataset.Parameter;

/**
 * Simple class that stores a list of Parameter instances
 * 
 * @author Andy Gibson
 * 
 */
public final class ParameterValues  implements Iterable<Parameter>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Parameter> values = new ArrayList<Parameter>();	
	
	public void add(Parameter parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException("Cannot pass a null parameter into the parameter list");
		}
		values.add(parameter); 
	}
	
	public Parameter add(String name,Object value) {
		Parameter parameter = new Parameter(name,value);
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
}
