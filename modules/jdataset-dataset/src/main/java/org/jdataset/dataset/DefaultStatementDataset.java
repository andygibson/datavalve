package org.jdataset.dataset;

import org.jdataset.StatementManager;
import org.jdataset.provider.StatementDataProvider;

/**
 * 
 * This class combines a {@link Dataset} that implements the
 * {@link ObjectDataset} interface and adds an implementation of the
 * {@link StatementDataProvider} interface as defined in the
 * {@link StatementDataset} interface. This allows us to wrap a
 * {@link ObjectDataset} and an {@link StatementDataProvider} interface in
 * a single object for convenience.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object this dataset returns
 */
public class DefaultStatementDataset<T> extends DefaultParameterizedDataset<T> implements StatementDataset<T> {

	private static final long serialVersionUID = 1L;

	protected StatementDataProvider<T> getCastProvider() {
		return (StatementDataProvider<T>) super.getProvider();
	}
	
	public DefaultStatementDataset(StatementDataProvider<T> provider) {
		super(provider);
	}


	public StatementManager getStatementHandler() {	
		return getCastProvider().getStatementHandler();
	}

	public void init(Class<? extends Object> clazz, String prefix) {
		getCastProvider().init(clazz, prefix);
	}
	
}
