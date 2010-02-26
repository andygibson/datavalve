package org.jdataset.dataset;

import org.jdataset.DataProvider;

/**
 * Concrete instance of the {@link GenericProviderDataset} that fixes the
 * generic provider type to a simple {@link DataProvider}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object returned from the provider
 */
public class Dataset<T> extends GenericProviderDataset<T, DataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public Dataset(DataProvider<T> provider) {
		super(provider);
	}

}
