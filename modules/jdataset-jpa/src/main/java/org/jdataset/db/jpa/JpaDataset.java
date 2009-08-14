package org.jdataset.db.jpa;

import javax.persistence.Query;

/**
 * JPA based query that uses the Ejbql syntax for querying.
 *  
 * @author Andy Gibson
 *
 * @param <T>
 */
public class JpaDataset<T> extends AbstractJpaDataset<T> {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Query createJpaQuery(String ql) {		
		return getEntityManager().createQuery(ql);
	}

}
