package org.jdataset.impl.provider.jpa;

import org.jdataset.dataset.GenericProviderDataset;
import org.jdataset.provider.QueryDataProvider;

public class JpaDataset<T> extends GenericProviderDataset<T, QueryDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public JpaDataset(AbstractJpaDataProvider<T> provider) {
		super(provider);
	}

}
