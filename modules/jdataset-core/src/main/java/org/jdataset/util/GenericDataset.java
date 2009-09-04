package org.jdataset.util;

import java.util.List;

import org.jdataset.AbstractDataset;
import org.jdataset.IPaginator;
import org.jdataset.provider.IDataProvider;

/**
 * This class is a generic dataset for any type of provider where the provider can be accessed in its native type.
 * 
 * <pre>
 * MyCustomProvider<Person> provider = new MyCustomProvider<Person>();
 * ds = new GenericDataset<Person,MyCustomProvider>();
 * ds.getProvider().someCustomMethod();
 * </pre>
 * @author GIBSOA01
 *
 * @param <T> Type of object the provider and the dataset will return
 * @param <P> Type of the provider this dataset uses
 */
public class GenericDataset<T, P extends IDataProvider<T>> extends AbstractDataset<T> {

	private static final long serialVersionUID = 1L;
	
	private final P provider;
	
	public GenericDataset(P provider) {
		super();
		this.provider = provider;
	}

	@Override
	protected Integer fetchResultCount() {
		return provider.fetchResultCount();
	}

	@Override
	protected List<T> fetchResults(IPaginator paginator) {
		return provider.fetchResults(paginator);
	}

	public P getProvider() {
		return provider;
	}
}
