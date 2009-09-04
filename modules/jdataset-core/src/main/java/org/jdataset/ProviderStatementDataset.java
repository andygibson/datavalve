package org.jdataset;

import org.jdataset.provider.IStatementDataProvider;


public class ProviderStatementDataset<T> extends GenericProviderDataset<T,IStatementDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public ProviderStatementDataset(IStatementDataProvider<T> provider) {
		super(provider);
	}


}
