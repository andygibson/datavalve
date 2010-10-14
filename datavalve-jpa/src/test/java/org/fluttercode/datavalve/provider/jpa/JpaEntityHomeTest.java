package org.fluttercode.datavalve.provider.jpa;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.fluttercode.datavalve.entity.jpa.JpaEntityHome;

public class JpaEntityHomeTest extends TestCase {

	private EntityManagerFactory emf;
	private EntityManager em;
	private transient Connection connection;
	private PersonHome home;
	
	public class PersonHome extends JpaEntityHome<Person> {
		
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

		emf = Persistence.createEntityManagerFactory("testPU");
		em = emf.createEntityManager();
		home = new PersonHome();
		home.setEntityManager(em);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		if (em != null) {
			em.close();
		}

		if (emf != null) {
			emf.close();
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
		em.getTransaction().begin();
		home.insert();
		em.getTransaction().commit();
		return home.getEntity();		
	}
	
	public Person doCleanLoad(Long id) {
		em.clear();
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
		em.getTransaction().begin();
		home.insert();
		em.getTransaction().commit();
		assertTrue(home.isManaged());
				

		assertNotNull(home.getEntity());
		Long id = home.getEntity().getId();
		assertNotNull(id);
		em.clear();
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
		em.clear();
		PersonHome updateHome = new PersonHome();
		updateHome.setEntityManager(em);
		assertFalse(updateHome.isManaged());
		updateHome.setId(p.getId());		
		
		updateHome.getEntity().setLastName("Jones");
		assertTrue(updateHome.isManaged());
		em.getTransaction().begin();
		updateHome.update();
		em.getTransaction().commit();
		
		Person modPerson = doCleanLoad(p.getId());
		assertEquals("Jones", modPerson.getLastName());
		
		
		
	}
}
