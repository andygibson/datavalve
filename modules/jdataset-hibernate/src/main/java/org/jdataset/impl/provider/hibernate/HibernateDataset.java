package org.jdataset.impl.provider.hibernate;

import org.jdataset.dataset.GenericProviderDataset;
import org.jdataset.provider.QueryDataProvider;


public class HibernateDataset<T> extends GenericProviderDataset<T, QueryDataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public HibernateDataset(HibernateDataProvider<T> provider) {
		super(provider);
	}

}
 