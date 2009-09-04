package org.jdataset;

import org.jdataset.provider.IParameterizedDataProvider;


public class ParameterizedDataset<T> extends GenericProviderDataset<T,IParameterizedDataProvider<T>>  {

	private static final long serialVersionUID = 1L;
	
	public ParameterizedDataset(IParameterizedDataProvider<T> provider) {
		super(provider);
	}


}
