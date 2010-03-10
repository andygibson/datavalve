package org.jdataset.samples.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jdataset.impl.InMemoryDataProvider;

public class InMemoryIntegerProvider extends InMemoryDataProvider<Integer>
		implements Serializable {

	private class IntegerSorter implements Comparator<Integer>, Serializable {

		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}
	}

	public InMemoryIntegerProvider() {
		getOrderKeyMap().put("default", new IntegerSorter());
	}

	@Override
	protected List<Integer> fetchBackingData() {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 1; i <= 100; i++) {
			result.add(i);
		}
		return result;
	}
}
