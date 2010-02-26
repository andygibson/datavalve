package org.jdataset.dataset;

import java.util.List;
import java.util.Map;

import org.jdataset.ParameterResolver;
import org.jdataset.provider.QueryDataProvider;

public class QueryDataset<T> extends
		GenericProviderDataset<T, QueryDataProvider<T>> implements
		QueryDataProvider<T> {

	private static final long serialVersionUID = 1L;

	public QueryDataset(QueryDataProvider<T> provider) {
		super(provider);
	}

	public void addRestriction(String restriction) {
		getProvider().addRestriction(restriction);
	}

	public boolean addRestriction(String restriction, Object value) {
		return getProvider().addRestriction(restriction, value);
	}

	public boolean addRestriction(String restriction, String testValue,
			String paramValue) {
		return getProvider().addRestriction(restriction, testValue, paramValue);
	}

	public boolean addRestriction(String restriction, Object testValue,
			Object paramValue) {
		return getProvider().addRestriction(restriction, testValue, paramValue);
	}

	public Map<String, String> getOrderKeyMap() {
		return getProvider().getOrderKeyMap();
	}

	public List<String> getRestrictions() {
		return getProvider().getRestrictions();
	}

	public void setOrderKeyMap(Map<String, String> orderKeyMap) {
		getProvider().setOrderKeyMap(orderKeyMap);
	}

	public void setRestrictions(List<String> restrictions) {
		getProvider().setRestrictions(restrictions);
	}

	public String getCountStatement() {
		return getProvider().getCountStatement();
	}

	public String getSelectStatement() {
		return getProvider().getSelectStatement();
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
