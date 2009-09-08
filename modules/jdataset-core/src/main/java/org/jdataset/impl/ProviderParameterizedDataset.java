package org.jdataset.impl;

import org.jdataset.provider.ParameterizedDataProvider;


public class ProviderParameterizedDataset<T> extends GenericProviderDataset<T,ParameterizedDataProvider<T>>  {

	private static final long serialVersionUID = 1L;
	
	public ProviderParameterizedDataset(ParameterizedDataProvider<T> provider) {
		super(provider);
	}


}
