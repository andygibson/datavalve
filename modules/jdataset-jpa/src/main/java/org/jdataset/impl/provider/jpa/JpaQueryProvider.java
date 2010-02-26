package org.jdataset.impl.provider.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * JPA based query that uses the Ejbql syntax for querying.
 *  
 * @author Andy Gibson
 *
 * @param <T>
 */
public class JpaQueryProvider<T> extends AbstractJpaDataProvider<T> {

	public JpaQueryProvider() {
		// TODO Auto-generated constructor stub
	}
	
	public JpaQueryProvider(EntityManager entityManager) {
		super(entityManager);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Query createJpaQuery(String ql) {		
		return getEntityManager().createQuery(ql);
	}
}
