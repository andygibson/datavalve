package org.jdataset.dataset;

import java.util.List;
import java.util.Map;

import org.jdataset.OrderManager;
import org.jdataset.RestrictionManager;
import org.jdataset.provider.QueryDataProvider;

/**
 * 
 * This class combines a {@link Dataset} that implements the
 * {@link ObjectDataset} interface and adds an implementation of the
 * {@link QueryDataProvider} interface as defined in the {@link QueryDataset}
 * interface. This allows us to wrap a {@link ObjectDataset} and an
 * {@link QueryDataProvider} interface in a single object for convenience.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object this dataset returns
 */
public class DefaultQueryDataset<T> extends DefaultStatementDataset<T>
		implements QueryDataset<T> {

	public DefaultQueryDataset(QueryDataProvider<T> provider) {
		super(provider);
	}

	private static final long serialVersionUID = 1L;

	protected QueryDataProvider<T> getCastProvider() {
		return (QueryDataProvider<T>) super.getProvider();
	}
	public RestrictionManager getRestrictionHandler() {
		return getCastProvider().getRestrictionHandler();
	}

	public OrderManager getOrderHandler() {
		return getCastProvider().getOrderHandler();
	}
}
