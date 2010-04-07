/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.wicketdemo;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.hibernate.Session;
import org.fluttercode.spigot.samples.wicketdemo.model.ModelBuilder;
import org.fluttercode.spigot.samples.wicketdemo.search.SqlSearchPage;



/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
/**
 * @author Andy Gibson
 * 
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

	@SuppressWarnings("deprecation")
	public Connection getConnection() {
		// we are lazily just using the hibernate JDBC connection rather than
		// setting up our own for the JDBC examples.
		return getHibernateSession().connection();

	}

	@Override
	public Class<? extends Page> getHomePage() {
		return SqlSearchPage.class;
	}
}
