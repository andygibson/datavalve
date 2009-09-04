package org.jdataset.combo;

import java.util.Map;

import org.jdataset.params.ParameterResolver;
import org.jdataset.provider.IParameterizedDataProvider;

public class ParameterizedDataset<T> extends ObjectDataset<T>  implements IParameterizedDataset<T> {

	private static final long serialVersionUID = 1L;

	public ParameterizedDataset(IParameterizedDataProvider<T> provider) {
		super(provider);
	}

	public IParameterizedDataProvider<T> getProvider() {
		return (IParameterizedDataProvider<T>) super.getProvider();
	}
	
	public void addParameter(String name, Object value) {
		getProvider().addParameter(name, value);
	}

	public void addParameterResolver(ParameterResolver resolver) {
		getProvider().addParameterResolver(resolver);
	}

	public Map<String, Object> getParameters() {
		return getProvider().getParameters();
	}

	public Object resolveParameter(String name) {
		return getProvider().resolveParameter(name);
	}

	public void setParameters(Map<String, Object> parameters) {
		getProvider().setParameters(parameters);
	}
}
