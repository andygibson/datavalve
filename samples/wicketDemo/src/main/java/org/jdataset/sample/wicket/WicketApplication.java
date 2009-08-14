package org.jdataset.sample.wicket;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.hibernate.Session;
import org.jdataset.sample.wicket.search.SqlSearchPage;
import org.phonelist.model.ModelBuilder;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	private EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("testPU");
		}
		return entityManagerFactory;
	}

	public EntityManager createEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	/**
	 * Constructor
	 */
	public WicketApplication() {
	}

	@Override
	protected void init() {
		super.init();
		ModelBuilder.execute(createEntityManager(), 1000);
	}

	public Session getHibernateSession() {
		return (Session) createEntityManager().getDelegate();
	}

	public Connection getConnection() {
		return getHibernateSession().connection();
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return SqlSearchPage.class;
	}
}
