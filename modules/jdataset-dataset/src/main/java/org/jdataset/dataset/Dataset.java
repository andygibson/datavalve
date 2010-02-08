package org.jdataset.dataset;

import org.jdataset.provider.DataProvider;


public class Dataset<T> extends GenericProviderDataset<T,DataProvider<T>>  {

	private static final long serialVersionUID = 1L;
	
	public Dataset(DataProvider<T> provider) {
		super(provider);
	}


}
