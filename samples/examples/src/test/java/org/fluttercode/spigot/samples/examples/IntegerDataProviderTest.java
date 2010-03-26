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

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * Test cases for the {@link IntegerDataProvider} example from the documentation
 * 
 * @author Andy Gibson
 * 
 */
public class IntegerDataProviderTest extends
		AbstractObjectDatasetJUnitTest<Integer> {

	private static final long serialVersionUID = 1L;
	
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
