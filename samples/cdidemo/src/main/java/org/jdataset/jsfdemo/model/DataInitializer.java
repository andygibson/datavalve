package org.jdataset.jsfdemo.model;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.inject.Singleton;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jdataset.testing.TestDataFactory;

@ApplicationScoped
public class DataInitializer {

	private SessionFactory sessionFactory;
	private Session session;
	private Connection connection;
	public static final int RECORD_COUNT = 150;

	public void init() {
		System.out.println("Starting up db");

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			System.out.println("Got class");
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
					TestDataFactory.getLastName(), TestDataFactory
							.getNumberText(10));

			for (int or = 0; or < 10; or++) {
				Order order = new Order(i * 10 + or, p);
				session.persist(order);
			}
			session.persist(p);
		}
		session.getTransaction().commit();
	}

	@Produces
	public Session getSession() {
		if (session == null) {
			init();
		}
		return session;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Connection getConnection() {
		return connection;
	}

}
