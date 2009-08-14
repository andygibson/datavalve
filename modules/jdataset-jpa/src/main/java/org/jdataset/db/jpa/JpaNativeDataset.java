package org.jdataset.db.jpa;

import javax.persistence.Query;

/**
 * A JPA based dataset that uses a native query language to generate the
 * results.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public class JpaNativeDataset<T> extends AbstractJpaDataset<T> {

	private static final long serialVersionUID = 1L;

	@Override
	protected Query createJpaQuery(String ql) {
		return getEntityManager().createNativeQuery(ql, getEntityClass());
	}

}
