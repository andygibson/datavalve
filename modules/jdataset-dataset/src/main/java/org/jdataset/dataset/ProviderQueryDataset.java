package org.jdataset.dataset;

import org.jdataset.provider.QueryDataProvider;

public class ProviderQueryDataset<T> extends
		GenericProviderDataset<T, QueryDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public ProviderQueryDataset(QueryDataProvider<T> provider) {
		super(provider);
	}

}
