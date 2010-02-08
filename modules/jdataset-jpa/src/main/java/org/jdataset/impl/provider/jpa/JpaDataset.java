package org.jdataset.impl.provider.jpa;

import org.jdataset.dataset.GenericProviderDataset;
import org.jdataset.provider.QueryDataProvider;

/**
 * Creates a helper class that encapsulates the Query base dataset with a an
 * {@link AbstractJpaDataProvider}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object this dataset returns
 */
public class JpaDataset<T> extends
		GenericProviderDataset<T, QueryDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public JpaDataset(AbstractJpaDataProvider<T> provider) {
		super(provider);
	}

}
