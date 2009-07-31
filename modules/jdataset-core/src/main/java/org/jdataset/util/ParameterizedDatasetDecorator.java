package org.jdataset.util;

import java.util.Map;

import org.jdataset.ParameterResolver;
import org.jdataset.ParameterizedDataset;

public class ParameterizedDatasetDecorator<T> extends ObjectDatasetDecorator<T> implements ParameterizedDataset<T> {

	private static final long serialVersionUID = 1L;
	
	private ParameterizedDataset<T> parameterizedDataset;

	public ParameterizedDatasetDecorator(ParameterizedDataset<T> parameterizedDataset) {
		super(parameterizedDataset);
		this.parameterizedDataset = parameterizedDataset;
	}

	public void addParameter(String name, Object value) {
		parameterizedDataset.addParameter(name, value);
	}

	public void addParameterResolver(ParameterResolver resolver) {
		parameterizedDataset.addParameterResolver(resolver);
	}

	public Map<String, Object> getParameters() {
		return parameterizedDataset.getParameters();
	}

	public Object resolveParameter(String name) {
		return parameterizedDataset.resolveParameter(name);
	}

	public void setParameters(Map<String, Object> parameters) {
		parameterizedDataset.setParameters(parameters);
	}	
}
