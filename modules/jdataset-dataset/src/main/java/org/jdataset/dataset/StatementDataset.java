package org.jdataset.dataset;

import java.util.Map;

import org.jdataset.ParameterResolver;
import org.jdataset.provider.StatementDataProvider;


public class StatementDataset<T> extends GenericProviderDataset<T,StatementDataProvider<T>> implements StatementDataProvider<T> {

	private static final long serialVersionUID = 1L;

	public StatementDataset(StatementDataProvider<T> provider) {
		super(provider);
	}

	public String getCountStatement() {
		return getProvider().getCountStatement();
	}

	public String getSelectStatement() {
		return getProvider().getCountStatement();
	}

	public void init(Class<? extends Object> clazz, String prefix) {
		getProvider().init(clazz, prefix);		
	}

	public void setCountStatement(String countStatement) {
		getProvider().setCountStatement(countStatement);		
	}

	public void setSelectStatement(String selectStatement) {
		getProvider().setSelectStatement(selectStatement);		
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
