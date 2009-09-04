package org.jdataset;

import org.jdataset.provider.IQueryDataProvider;


public class QueryDataset<T> extends GenericProviderDataset<T,IQueryDataProvider<T>> {

	private static final long serialVersionUID = 1L;
	
	public QueryDataset(IQueryDataProvider<T> provider) {
		super(provider);
	}
	
}
