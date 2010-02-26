package org.jdataset.impl.provider.jpa;

import javax.persistence.EntityManager;

import org.jdataset.provider.QueryDataProvider;

public interface JpaDataProvider<T> extends QueryDataProvider<T> {

	EntityManager getEntityManager();
	void setEntityManager(EntityManager entityManager);
}
