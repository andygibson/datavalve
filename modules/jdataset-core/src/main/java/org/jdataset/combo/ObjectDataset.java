package org.jdataset.combo;

import org.jdataset.IObjectDataset;
import org.jdataset.Dataset;
import org.jdataset.provider.IDataProvider;

public class ObjectDataset<T> extends Dataset<T> implements IObjectDataset<T> {

	public ObjectDataset(IDataProvider<T> provider) {
		super(provider);
	}

	private static final long serialVersionUID = 1L;
	
}
