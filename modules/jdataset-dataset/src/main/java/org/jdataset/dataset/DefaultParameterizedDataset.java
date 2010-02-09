package org.jdataset.dataset;

import org.jdataset.ParameterManager;
import org.jdataset.provider.ParameterizedDataProvider;

/**
 * 
 * This class combines a {@link Dataset} that implements the
 * {@link ObjectDataset} interface and adds an implementation of the
 * {@link ParameterizedDataProvider} interface as defined in the
 * {@link ParameterizedDataset} interface. This allows us to wrap a
 * {@link ObjectDataset} and an {@link ParameterizedDataProvider} interface in
 * a single object for convenience.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object this dataset returns
 */
public class DefaultParameterizedDataset<T> extends Dataset<T> implements
		ParameterizedDataset<T> {

	private static final long serialVersionUID = 1L;

	public DefaultParameterizedDataset(ParameterizedDataProvider<T> provider) {
		super(provider);
	}

	public ParameterizedDataProvider<T> getProvider() {
		return (ParameterizedDataProvider<T>) super.getProvider();
	}

	public ParameterManager getParameterHandler() {
		return getProvider().getParameterHandler();
	}
	
	

}
