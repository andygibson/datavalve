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

package org.fluttercode.spigot.swingdemo;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.fluttercode.spigot.swingdemo.model.Order;
import org.fluttercode.spigot.swingdemo.model.Person;
import org.fluttercode.spigot.testing.TestDataFactory;
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
		generateTestData();

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
