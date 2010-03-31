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

package org.fluttercode.spigot.provider.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;
import org.fluttercode.spigot.provider.jpa.JpaDataProvider;
import org.fluttercode.spigot.provider.jpa.JpaQueryProvider;
import org.fluttercode.spigot.testing.TestDataFactory;
import org.fluttercode.spigot.testing.junit.AbstractObjectDatasetJUnitTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andy Gibson
 * 
 */
public class GenericJpaDatasetTest extends AbstractObjectDatasetJUnitTest<Person> {

	private JpaDataset<Person> dataset;
	
	private class JpaDataset<T> extends Dataset<T, JpaDataProvider<T>> {

		private static final long serialVersionUID = 1L;

		public JpaDataset(JpaDataProvider<T> provider) {
			super(provider);			
		}
		
	}
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(GenericJpaDatasetTest.class);

	private EntityManagerFactory emf;
	private EntityManager em;
	private transient Connection connection;

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
		generateTestData();
		dataset = buildQueryDataset();
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

	public void generateTestData() {
		em.getTransaction().begin();
		for (int i = 0; i < 30; i++) {
			Person p = new Person(TestDataFactory.getFirstName(),
					TestDataFactory.getLastName());
			for (int or = 0; or < 10; or++) {
				Order order = new Order(i * 10 + or, p);
				em.persist(order);
			}
			em.persist(p);
		}
		em.getTransaction().commit();

	}

	public void testVerifydataGeneration() {

		Person p = new Person("Andy", "Gibson");
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();

		@SuppressWarnings("unchecked")
		List<Person> people = em.createQuery("select p from Person p")
				.getResultList();
		assertNotNull(people);
		assertEquals(31, people.size());

		Long res = (Long) em.createQuery(
				"select count(p) from Order p where p.person.id = 10")
				.getSingleResult();
		assertEquals(10, res.longValue());
	}

	public void testResultCount() {	
		long result = dataset.getResultCount();
		assertEquals(30, result);

		dataset.getProvider().getRestrictions().add("p.id = 3");
		dataset.invalidateResultInfo();
		result = dataset.getResultCount();
		assertEquals(1, result);

	}

	public void testSimpleParameter() {				
		dataset.getProvider().getRestrictions().add("p.id = :personId");
		dataset.getProvider().getParameters().put("personId", 4l);
		List<Person> result = dataset.getResultList();
		Long val = (Long)dataset.getProvider().resolveParameter(":personId");
		assertNotNull(val);
		assertEquals(4, val.intValue());

		assertNotNull(result);

		assertEquals(1, result.size());
		assertEquals(1, dataset.getPage());
		Person p = result.get(0);
		assertEquals(new Long(4), p.getId());
	}

	public void testMissingParameter() {		
		dataset.getProvider().getRestrictions().add("p.id = #{personId}");
		List<Person> result = dataset.getResultList();

		assertNotNull(result);
		assertEquals(1, dataset.getPage());
		assertEquals(30, result.size());

	}

	public void testNullParameter() {
		dataset.getProvider().getRestrictions().add("p.id = #{personId}");
		List<Person> result = dataset.getResultList();
		dataset.getProvider().getParameters().put("personId", null);

		assertNotNull(result);

		assertEquals(30, result.size());
	}

	public void testPagination() {		
		
		assertEquals(1, dataset.getPage());
		assertEquals(false, dataset.isNextAvailable());
		assertEquals(false, dataset.isPreviousAvailable());

		dataset.setMaxRows(10);
		assertEquals(1, dataset.getPage());

		assertEquals(true, dataset.isNextAvailable());
		assertEquals(false, dataset.isPreviousAvailable());

		dataset.next();
		assertEquals(2, dataset.getPage());
		assertEquals(true, dataset.isNextAvailable());
		assertEquals(true, dataset.isPreviousAvailable());

		dataset.previous();
		assertEquals(1, dataset.getPage());
		assertEquals(true, dataset.isNextAvailable());
		assertEquals(false, dataset.isPreviousAvailable());

		dataset.last();
		assertEquals(3, dataset.getPage());
		assertEquals(false, dataset.isNextAvailable());
		assertEquals(true, dataset.isPreviousAvailable());

		dataset.previous();
		assertEquals(2, dataset.getPage());
		assertEquals(true, dataset.isNextAvailable());
		assertEquals(true, dataset.isPreviousAvailable());

	}

	public void testParameterResolverRepeatsEval() {					
		dataset.getProvider().getRestrictions().add("p.id = #{id}");
		dataset.getProvider().getRestrictions().add("p.id = #{id}");

		dataset.getProvider().addParameterResolver(new ParameterResolver() {

			long id = 20;

			public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,Parameter parameter) {
				if (parameter.getName().equals("#{id}")) {
					parameter.setValue(id++);
					return true;
				}
				return false;
			}

			public boolean acceptParameter(String parameter) {
				
				boolean val = parameter.startsWith("#{") && parameter.endsWith("}");
				return val;
			}
			
		});
		List<Person> results = dataset.getResultList();
		assertNotNull(results);
		assertEquals(0, results.size());
		assertEquals(0, dataset.getResultCount().intValue());

	}

	public void testQueryWithOrdering() {
		ObjectDataset<Person> qry = buildObjectDataset();
		qry.setOrderKey("id");
		// check record count
		assertEquals(getDataRowCount(), qry.getResultCount().intValue());
		List<Person> results = qry.getResultList();
		assertNotNull(results);
		assertEquals(getDataRowCount(), results.size());
	}

	public void testOrderByAscNonPaged() {
		ObjectDataset<Person> qry = buildObjectDataset();
		qry.setOrderKey("id");
		performOrderChecks(qry,true);
	}

	public void testOrderByAscPaged() {
		ObjectDataset<Person> qry = buildObjectDataset();
		qry.setMaxRows(7);
		qry.setOrderKey("id");
		performOrderChecks(qry,true);
	}

	public void testOrderByDescNonPaged() {
		ObjectDataset<Person> qry = buildObjectDataset();
		qry.setOrderKey("id");
		performOrderChecks(qry,false);
	}

	public void testOrderByDescPaged() {
		ObjectDataset<Person> qry = buildObjectDataset();
		qry.setMaxRows(7);
		qry.setOrderKey("id");
		performOrderChecks(qry,false);
	}

	protected void performOrderChecks(ObjectDataset<Person> qry, boolean isAsc) {
		Long lastValue = null;
		do {
			List<Person> results = qry.getResultList();
			for (int i = 0; i < results.size(); i++) {
				Person p = results.get(i);
				if (lastValue != null) {
					if (isAsc) {
						assertTrue(p.getId() > lastValue);
				 	} else {
				 		assertTrue(p.getId() < lastValue);
				 	}
				}
			}
			qry.next();
		} while (qry.isNextAvailable());
	}
	
	@Override
	public ObjectDataset<Person> buildObjectDataset() {
		return dataset;
	}
	
	protected JpaDataset<Person> buildQueryDataset() {
		JpaDataProvider<Person> provider = new JpaQueryProvider<Person>(em);		
		provider.setSelectStatement("select p from Person p");
		provider.setCountStatement("select count(p) from Person p");
		provider.getOrderKeyMap().put("id", "p.id");
		provider.getOrderKeyMap().put("name", "p.lastName,p.firstName");
		provider.getOrderKeyMap().put("phone", "p.phone");
		return new JpaDataset<Person>(provider);
	}

	@Override
	public int getDataRowCount() {
		return 30;
	}
	
}
