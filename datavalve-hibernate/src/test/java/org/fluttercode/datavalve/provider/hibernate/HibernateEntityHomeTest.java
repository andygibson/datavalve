package org.fluttercode.datavalve.provider.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

import org.fluttercode.datavalve.dataset.QueryDataset;
import org.fluttercode.datavalve.entity.hibernate.HibernateEntityHome;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateEntityHomeTest extends TestCase {

	private SessionFactory sessionFactory;
	private Session session;
	private transient Connection connection;
	private PersonHome home;
	
	public class PersonHome extends HibernateEntityHome<Person> {
		
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// start up db
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection(
					"jdbc:hsqldb:mem:unit-testing-jpa", "sa", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception during HSQL database startup.");
		}

		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.addAnnotatedClass(Order.class);
		cfg.addAnnotatedClass(Person.class);
		sessionFactory = cfg.configure().buildSessionFactory();
		session = sessionFactory.openSession();
		home = new PersonHome();
		home.setSession(session);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		if (session != null) {
			session.close();
		}

		if (sessionFactory != null) {
			sessionFactory.close();
		}

		// shutdown db
		try {
			connection.createStatement().execute("SHUTDOWN");
			connection.close();
		} catch (Exception ex) {
		}

	}
	
	public Person doInsert() {
		home.setEntity(null);
		home.getEntity().setFirstName("Tim");
		home.getEntity().setLastName("Smith");		
		session.beginTransaction();
		home.insert();
		session.getTransaction().commit();
		return home.getEntity();		
	}
	
	public Person doCleanLoad(Long id) {
		session.clear();
		home.setEntity(null);
		home.setId(id);
		return home.getEntity();		
	}
	
	public void testInsert() {
		//do the insert here so we can test the managed flag 
		assertFalse(home.isManaged());
		Person person = home.getEntity();
		assertFalse(home.isManaged());
		home.getEntity().setFirstName("Tim");
		home.getEntity().setLastName("Smith");
		session.getTransaction().begin();
		home.insert();
		session.getTransaction().commit();
		assertTrue(home.isManaged());
				

		assertNotNull(home.getEntity());
		Long id = home.getEntity().getId();
		assertNotNull(id);
		session.clear();
		home.setEntity(null);
		home.setId(id);
		Person p = home.getEntity();		
		assertNotNull(p);
		assertNotSame(person, p);
		assertEquals("Tim", p.getFirstName());
		assertEquals("Smith", p.getLastName());		
	}
	
	public void testUpdateAfterInsert() {
		Person p = doInsert();
		session.clear();
		PersonHome updateHome = new PersonHome();
		updateHome.setSession(session);
		assertFalse(updateHome.isManaged());
		updateHome.setId(p.getId());		
		
		updateHome.getEntity().setLastName("Jones");
		assertTrue(updateHome.isManaged());
		session.getTransaction().begin();
		updateHome.update();
		session.getTransaction().commit();
		
		Person modPerson = doCleanLoad(p.getId());
		assertEquals("Jones", modPerson.getLastName());
		
		
		
	}
}
