package org.jdataset;

import java.util.List;

import org.jdataset.provider.IDataProvider;

/**
 * A Generic version of the provider dataset that extends the
 * {@link AbstractDataset} and holds a typed {@link IDataProvider} reference.
 * 
 * @author Andy Gibson
 * 
 * 
 * 
 * @param <T>
 *            The type of object this dataset will end up returning
 * @param <P>
 *            The type of data provider used to fetch the data
 */
public class GenericProviderDataset<T, P extends IDataProvider<T>> extends
		AbstractDataset<T> {

	private static final long serialVersionUID = 1L;

	private final P provider;

	public GenericProviderDataset(P provider) {
		super();
		this.provider = provider;
	}

	public Integer fetchResultCount() {
		return provider.fetchResultCount();
	}

	public List<T> fetchResults(IPaginator paginator) {
		return provider.fetchResults(paginator);
	}

	public P getProvider() {
		return provider;
	}
}
