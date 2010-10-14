package org.fluttercode.datavalve.entity.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.fluttercode.datavalve.entity.AbstractEntityHome;

public class JpaEntityHome<T> extends AbstractEntityHome<T> {

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	protected T doLoadEntity() {		
		return (T) getEntityManager().find(getEntityClass(), getId()); 
	}

	@Override
	protected void doInsert() {
		getEntityManager().persist(getEntity());		
	}

	@Override
	protected void doCancel() {
		getEntityManager().refresh(getEntity());		
	}
}
