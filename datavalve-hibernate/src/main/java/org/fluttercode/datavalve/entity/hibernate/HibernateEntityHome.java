package org.fluttercode.datavalve.entity.hibernate;

import java.io.Serializable;

import org.fluttercode.datavalve.entity.AbstractEntityHome;
import org.hibernate.Session;

public class HibernateEntityHome<T> extends
		AbstractEntityHome<T> {

	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	protected T doLoadEntity() {
		return (T) session.get(getEntityClass(), (Serializable) getId());
	}

	@Override
	protected void doCancel() {
		session.refresh(getEntity());
	}

	@Override
	protected void doInsert() {
		session.saveOrUpdate(getEntity());
	}

}
