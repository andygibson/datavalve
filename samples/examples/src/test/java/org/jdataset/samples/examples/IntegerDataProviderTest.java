package org.jdataset.samples.examples;

import java.util.List;

import org.jdataset.DataProvider;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * Test cases for the {@link IntegerDataProvider} example from the documentation
 * 
 * @author Andy Gibson
 * 
 */
public class IntegerDataProviderTest extends
		AbstractObjectDatasetJUnitTest<Integer> {

	@Override
	public ObjectDataset<Integer> buildObjectDataset() {
		DataProvider<Integer> provider = new IntegerDataProvider();		
		return new Dataset<Integer>(provider);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

	@Override
	public boolean includeSerializationTest() {
	
		return true;
	}
	
	public void testResultsPaginated() {
		ObjectDataset<Integer> ds = buildObjectDataset();
		ds.setMaxRows(10);
		Integer expected = 1;
		while (ds.isNextAvailable()) {
			List<Integer> results = ds.getResultList();
			for (Integer i : results) {
				assertEquals(expected, i);
				expected = expected + 1;
			}
			ds.next();
		}

	}

	public void testResultsIterated() {
		ObjectDataset<Integer> ds = buildObjectDataset();
		ds.setMaxRows(10);
		Integer expected = 1;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected = expected + 1;
		}
	}

	public void testResultsNonPaginated() {
		ObjectDataset<Integer> ds = buildObjectDataset();
		ds.setMaxRows(null);
		Integer expected = 1;

		List<Integer> results = ds.getResultList();
		assertEquals(100, results.size()); //check size
		for (Integer i : results) {
			assertEquals(expected, i);
			expected = expected + 1;
		}
	}	
	
}
