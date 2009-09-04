package org.jdataset.combo;

import java.util.List;
import java.util.Map;

import org.jdataset.provider.IQueryDataProvider;

public class QueryDataset<T> extends StatementDataset<T> implements
		IQueryDataset<T> {

	public QueryDataset(IQueryDataProvider<T> provider) {
		super(provider);
	}

	private static final long serialVersionUID = 1L;

	protected IQueryDataProvider<T> getCastProvider() {
		return (IQueryDataProvider<T>) super.getProvider();
	}

	public void addRestriction(String restriction) {
		getCastProvider().addRestriction(restriction);
	}

	public Map<String, String> getOrderKeyMap() {
		return getCastProvider().getOrderKeyMap();
	}

	public List<String> getRestrictions() {
		return getCastProvider().getRestrictions();
	}

	public void setOrderKeyMap(Map<String, String> orderKeyMap) {
		getCastProvider().setOrderKeyMap(orderKeyMap);
	}

	public void setRestrictions(List<String> restrictions) {
		getCastProvider().setRestrictions(restrictions);
	}

}
