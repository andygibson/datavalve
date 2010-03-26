/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.samples.wicket;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.hibernate.Session;
import org.fluttercode.spigot.samples.wicket.search.SqlSearchPage;
import org.fluttercode.spigot.samples.wicketdemo.model.ModelBuilder;

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
