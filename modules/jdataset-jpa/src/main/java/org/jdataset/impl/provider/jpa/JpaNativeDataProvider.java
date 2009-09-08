package org.jdataset.impl.provider.jpa;

import javax.persistence.Query;

/**
 * A JPA based dataset that uses a native query language to generate the
 * results.
 * 
 * @author Andy Gibson
 * 
 * @param <T> Type of object this dataset contains
 */
public class JpaNativeDataProvider<T> extends AbstractJpaDataProvider<T> {

	private static final long serialVersionUID = 1L;

	@Override
	protected Query createJpaQuery(String ql) {
		return getEntityManager().createNativeQuery(ql, getEntityClass());
	}

}
