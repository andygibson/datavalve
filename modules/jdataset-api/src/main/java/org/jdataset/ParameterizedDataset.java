package org.jdataset;

import java.util.Map;


/**
 * Extension of the object dataset that includes parameterization of the
 * dataset. This introduces methods that allows you to define parameters and
 * also reference parameter resolvers to extend the parameter resolution beyond
 * key-value pairs.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface ParameterizedDataset<T> extends ObjectDataset<T> {

	public abstract Map<String, Object> getParameters();

	public abstract void setParameters(Map<String, Object> parameters);

	public abstract void addParameter(String name, Object value);

	public void addParameterResolver(ParameterResolver resolver);

	public Object resolveParameter(String name);

}
