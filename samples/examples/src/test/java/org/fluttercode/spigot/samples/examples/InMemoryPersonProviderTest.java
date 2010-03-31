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

package org.fluttercode.spigot.samples.examples;

import java.util.List;

import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.provider.InMemoryAdapterProvider;
import org.fluttercode.spigot.testing.junit.AbstractObjectDatasetJUnitTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andy Gibson
 * 
 */
public class InMemoryPersonProviderTest extends AbstractObjectDatasetJUnitTest<Person> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log =  LoggerFactory.getLogger(InMemoryPersonProviderTest.class);
	@Override
	public ObjectDataset<Person> buildObjectDataset() {
		InMemoryPersonProvider provider = new InMemoryPersonProvider();
		return new Dataset<Person,InMemoryPersonProvider>(provider);
	}

	@Override
	public int getDataRowCount() {
		return InMemoryPersonProvider.PERSON_COUNT;
	}

	public void testIdOrdering() {
		ObjectDataset<Person> ds = buildObjectDataset();
		ds.setOrderKey("id");
		ds.setOrderAscending(false);
		ds.setMaxRows(10);
		//check descending
		Long id = null;
		
		for (Person p : ds) {
			if (id != null) {
				assertTrue("Next Id is not less than the last",p.getId() < id);
			}
			id = p.getId();
		}
		
		
		//now reverse the ordering
		ds.setOrderAscending(true);
		id = null;
		for (Person p : ds) {
			if (id != null) {
				assertTrue("Next Id is not greater than the last, expected "+id+" found "+ p.getId(),p.getId() > id);
			}
			id = p.getId();
		}
		
	}
		
	public void testNameOrdering() {
		ObjectDataset<Person> ds = buildObjectDataset();
		ds.setOrderKey("name");
		ds.setOrderAscending(false);
		ds.setMaxRows(10);
		//check descending
		String name = null;
		
		for (Person p : ds) {			
			if (name != null) {
				int diff = p.getName().compareToIgnoreCase(name);
				assertTrue("Next Id is not less than the last",diff < 0);
			}
			name = p.getName();
		}
		
		
		//now reverse the ordering
		ds.setOrderAscending(true);
		name = null;
		for (Person p : ds) {
			if (name != null) {
				int diff = p.getName().compareToIgnoreCase(name);
				assertTrue("Next Id is not greater than the last",diff > 0);
			}
			name = p.getName();
		}
		
	}
	
	public void testDualOrdering() {
		log.debug("Testing dual ordering");
		InMemoryPersonProvider provider = new InMemoryPersonProvider();
		InMemoryAdapterProvider<Person> providerAdapter = new  InMemoryAdapterProvider<Person>(provider);
		//still need to assign the ordering info
//		providerAdapter.getOrderKeyMap().put("id", new InMemoryPersonProvider.PersonIdComparator());
		//providerAdapter.getOrderKeyMap().put("name", new InMemoryPersonProvider.PersonNameComparator());		
		
		ObjectDataset<Person> ds = new Dataset<Person,InMemoryPersonProvider>(provider);
		ObjectDataset<Person> ds2 = new Dataset<Person,InMemoryAdapterProvider<Person>>(providerAdapter);
		
		
		ds.setOrderKey("name");
		ds.setOrderAscending(false);
		ds2.setOrderKey("name");
		ds2.setOrderAscending(true);
		
		List<Person> list1 = ds.getResultList();
		List<Person> list2 = ds2.getResultList();
		
		for (int i = 0;i < ds.getResultList().size();i++) {
			assertEquals(list1.get(i),list2.get(list2.size()-1-i));
		}
		
		//now try the other way
		ds.setOrderKey("id");
		ds.setOrderAscending(true);
		ds2.setOrderKey("id");
		ds2.setOrderAscending(false);
		
		list1 = ds.getResultList();
		list2 = ds2.getResultList();
		
		for (int i = 0;i < ds.getResultList().size();i++) {
			assertEquals(list1.get(i),list2.get(list2.size()-1-i));
			assertEquals(new Long(i+1),list1.get(i).getId());
			assertEquals(new Long(InMemoryPersonProvider.PERSON_COUNT-i),list2.get(i).getId());
		}
		
		
	}
}
