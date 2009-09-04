package org.jdataset;

import org.jdataset.provider.IDataProvider;


public class Dataset<T> extends GenericProviderDataset<T,IDataProvider<T>>  {

	private static final long serialVersionUID = 1L;
	
	public Dataset(IDataProvider<T> provider) {
		super(provider);
	}


}
