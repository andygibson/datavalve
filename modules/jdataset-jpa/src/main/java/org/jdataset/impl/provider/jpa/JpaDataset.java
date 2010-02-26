package org.jdataset.impl.provider.jpa;

import org.jdataset.dataset.GenericProviderDataset;

public class JpaDataset<T> extends GenericProviderDataset<T, JpaDataProviderIntf<T>> {

	private static final long serialVersionUID = 1L;

	public JpaDataset(JpaDataProviderIntf<T> provider) {
		super(provider);
	}

}
