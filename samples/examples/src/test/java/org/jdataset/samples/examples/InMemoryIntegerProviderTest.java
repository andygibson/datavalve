package org.jdataset.samples.examples;

import org.jdataset.DataProvider;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;

public class InMemoryIntegerProviderTest extends IntegerDataProviderTest {

	@Override
	public ObjectDataset<Integer> buildObjectDataset() {	
		DataProvider<Integer> provider = new InMemoryIntegerProvider();		
		return new Dataset<Integer>(provider);

	}
	
	public void testSortingWithoutKeySet() {
		ObjectDataset<Integer> ds = buildObjectDataset();
		
		ds.setOrderAscending(false);
		Integer expected = 1;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected++;
		}

		//now change order - results should stay the same
		ds.setOrderAscending(true);
		expected = 1;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected++;
		}

		//change again - result should NOT change
		ds.setOrderAscending(false);
		expected = 1;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected++;
		}
		
	}
	
	public void testSortingWithKeySet() {
		ObjectDataset<Integer> ds = buildObjectDataset();
		ds.setOrderAscending(false);
		ds.setOrderKey("default");
		Integer expected = 100;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected--;
		}

		//now change order - results should reverse
		ds.setOrderAscending(true);
		expected = 1;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected++;
		}

		//change again - results should reverse again to be descending
		ds.setOrderAscending(false);
		expected = 100;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected--;
		}
		
	}
	
}
