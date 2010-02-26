package org.jdataset.dataset;

import java.util.Map;

import org.jdataset.ParameterResolver;
import org.jdataset.provider.ParameterizedDataProvider;


public class ParameterizedDataset<T> extends GenericProviderDataset<T,ParameterizedDataProvider<T>>  implements ParameterizedDataProvider<T> {

	private static final long serialVersionUID = 1L;
	
	public ParameterizedDataset(ParameterizedDataProvider<T> provider) {
		super(provider);
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

	public Integer fetchResultCount() {
		return getProvider().fetchResultCount();
	}
}
