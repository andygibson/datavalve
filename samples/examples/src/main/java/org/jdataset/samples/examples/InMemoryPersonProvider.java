package org.jdataset.samples.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jdataset.impl.InMemoryDataProvider;
import org.jdataset.testing.TestDataFactory;

public class InMemoryPersonProvider extends InMemoryDataProvider<Person> {

	private static final long serialVersionUID = 1L;
	
	private List<Person> backingList;
	public static final int PERSON_COUNT = 30;

	/**
	 * Class for sorting two {@link Person} instances by {@link Person#getId()}
//	 * 
	 * @author Andy Gibson
	 * 
	 */
	public static class PersonIdComparator implements Comparator<Person>,
			Serializable {

		public int compare(Person o1, Person o2) {
			return (int) (o1.getId() - o2.getId());
		}
	}

	/**
	 * Class for sorting two {@link Person} instances by
	 * {@link Person#getLastName()} and then by {@link Person#getFirstName()}
	 * 
	 * @author Andy Gibson
	 * 
	 */
	public static  class PersonNameComparator implements Comparator<Person>,
			Serializable {

		public int compare(Person o1, Person o2) {
			int val = o1.getLastName().compareToIgnoreCase(o2.getLastName());
			//if first names match, then try comparing by the first name			
			if (val == 0) {
				val = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
			}
			return val;
		}
	}

	public InMemoryPersonProvider() {
		//add the order keys by default in the constructor
		getOrderKeyMap().put("id", new PersonIdComparator());
		getOrderKeyMap().put("name", new PersonNameComparator());
		//generate some random test data
		if (backingList == null) {
			backingList = new ArrayList<Person>(PERSON_COUNT);
			for (int i = 0; i < PERSON_COUNT; i++) {
				Person p = new Person(new Long(i + 1), TestDataFactory
						.getFirstName(), TestDataFactory.getLastName());
				backingList.add(p);
			}
		}		
	}

	@Override
	protected List<Person> fetchBackingData() {
		return backingList;
	}

}
