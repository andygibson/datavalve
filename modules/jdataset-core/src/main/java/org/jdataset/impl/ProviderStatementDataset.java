package org.jdataset.impl;

import org.jdataset.provider.StatementDataProvider;


public class ProviderStatementDataset<T> extends GenericProviderDataset<T,StatementDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public ProviderStatementDataset(StatementDataProvider<T> provider) {
		super(provider);
	}


}
