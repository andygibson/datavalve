package org.jdataset.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jdataset.ParameterManager;
import org.jdataset.ParameterResolver;

public class DefaultParameterManager implements ParameterManager,Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> parameters = new HashMap<String,Object>();
	private DefaultParameterResolverManager resolvers = new DefaultParameterResolverManager();
	
	public void addParameter(String name, Object value) {
		parameters.put(name,value);
	}

	public void addParameterResolver(ParameterResolver resolver) {
		resolvers.add(resolver);		
		
	}

	public Map<String, Object> getParameters() {
		return new HashMap<String, Object>(parameters);
	}

	public Object resolveParameter(String name) {
		if (name == null || name.trim().length() < 2) {
			return null;
		}
		//check if we have a value in the local parameter map
		String strippedName = name.substring(1);
		Object result = parameters.get(strippedName);
		if (result != null) {
			return result;
		}
		//if not, we defer to the parameters resolvers.
		return resolvers.resolveParameter(name);
	}

	public void setParameters(Map<String, Object> parameters) {
		parameters = new HashMap<String,Object>(parameters);
	}
}
 