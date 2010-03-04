package org.jdataset.dataset;

import java.util.List;

import org.jdataset.DataProvider;
import org.jdataset.Paginator;

/**
 * A Generic version of the provider dataset that extends the
 * {@link AbstractDataset} and holds a typed {@link DataProvider} reference.
 * 
 * <pre>
 * MyCustomProvider&lt;Person&gt; provider = new MyCustomProvider&lt;Person&gt;();
 * ds = new GenericDataset&lt;Person, MyCustomProvider&gt;();
 * ds.getProvider().someCustomMethod();
 * </pre>
 * 
 * We have 'helper' types for this with specific implementations for
 * parameterized,statement,and query provider types. These are currently implemented as {@link ParameterizedDataset}, {@link StatementDataset}, and {@link QueryDataset}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object this dataset will end up returning
 * @param <P>
 *            The type of data provider used to fetch the data
 */ 
public class GenericProviderDataset<T, P extends DataProvider<T>> extends
		AbstractDataset<T> {

	private static final long serialVersionUID = 1L;

	private final P provider;

	public GenericProviderDataset(P provider) {
		super();
		this.provider = provider;
	}

	public Integer loadResultCount() {
		return provider.fetchResultCount();
	}
	
	@Override
	protected List<T> loadResults(Paginator paginator) {
	
		return provider.fetchResults(paginator);
	}

	public List<T> fetchResults(Paginator paginator) {
		return provider.fetchResults(paginator);
	}

	public P getProvider() {
		return provider;
	}
}
