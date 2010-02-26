package org.jdataset.impl.provider.jpa;

import org.jdataset.dataset.GenericProviderDataset;

public class JpaDataset<T> extends GenericProviderDataset<T, JpaDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public JpaDataset(JpaDataProvider<T> provider) {
		super(provider);
	}

}
