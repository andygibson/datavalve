package org.jdataset.impl.provider.jpa;

import java.util.List;

import javax.persistence.Query;

import org.jdataset.impl.provider.DataQuery;

/**
 * JPA based query that uses the Ejbql syntax for querying.
 *  
 * @author Andy Gibson
 *
 * @param <T>
 */
public class JpaDataProvider<T> extends AbstractJpaDataProvider<T> {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Query createJpaQuery(String ql) {		
		return getEntityManager().createQuery(ql);
	}
}
