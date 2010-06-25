/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.samples.swingdemo;

import java.sql.Connection;
import java.sql.DriverManager;

import org.fluttercode.datavalve.samples.swingdemo.model.Order;
import org.fluttercode.datavalve.samples.swingdemo.model.Person;
import org.fluttercode.datavalve.testing.TestDataFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andy Gibson
 * 
 */
public class DataInitializer {

	private static Logger log = LoggerFactory.getLogger(DataInitializer.class);
	private SessionFactory sessionFactory;
	private Session session;
	private Connection connection;
	public  static final int RECORD_COUNT = 2000;
	

	public void init() {
		log.debug("Starting up db");
		
		try {
			Class.forName("org.hsqldb.jdbcDriver");		
			connection = DriverManager.getConnection(
					"jdbc:hsqldb:mem:swing-demo-jpa", "sa", "");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.addAnnotatedClass(Order.class);
		cfg.addAnnotatedClass(Person.class);
		sessionFactory = cfg.configure().buildSessionFactory();
		session = sessionFactory.openSession();
		log.debug("Generating test data");
		generateTestData();
		log.debug("Test data generation completed, starting app");
	}

	private void generateTestData() {

		session.getTransaction().begin();
		
		for (int i = 0; i < RECORD_COUNT; i++) {
			Person p = new Person(TestDataFactory.getFirstName(),
					TestDataFactory.getLastName(),TestDataFactory.getNumberText(10));
		   
			session.persist(p);
		}
		session.getTransaction().commit();
	}

	public Session getSession() {
		return session;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public Connection getConnection() {
		return connection;
	}
}
