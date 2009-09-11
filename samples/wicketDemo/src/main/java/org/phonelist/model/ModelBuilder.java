package org.phonelist.model;

import javax.persistence.EntityManager;

import org.jdataset.testing.TestDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates the test data
 * 
 * @author Andy Gibson
 * 
 */
public class ModelBuilder {

	private static Logger log = LoggerFactory.getLogger(ModelBuilder.class);

	public static void execute(EntityManager em, int count) {

		log.debug("Building model database with {} rows", count);
		em.getTransaction().begin();
		try {
			for (int i = 0; i < count; i++) {
				Person person = new Person();
				person.setFirstName(TestDataFactory.getFirstName());
				person.setLastName(TestDataFactory.getLastName());
				person.setPhone(TestDataFactory.getNumberText(10));
				em.persist(person);
				// commit every 20 rows
				if (i % 20 == 0) {
					em.getTransaction().commit();
					em.getTransaction().begin();
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

}
