package org.jdataset.swingdemo;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jdataset.swingdemo.model.Order;
import org.jdataset.swingdemo.model.Person;
import org.jdataset.testing.TestDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataInitializer {

	private static Logger log = LoggerFactory.getLogger(DataInitializer.class);
	private SessionFactory sessionFactory;
	private Session session;
	private Connection connection;
	public  static final int RECORD_COUNT = 1150;
	

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
		   
			for (int or = 0; or < 10; or++) {
				Order order = new Order(i * 10 + or, p);
				session.persist(order);
			}
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
