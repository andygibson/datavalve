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

package org.fluttercode.spigot.samples.wicketdemo.model;

import javax.persistence.EntityManager;

import org.fluttercode.spigot.testing.TestDataFactory;
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
