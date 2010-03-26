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

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;

/**
 * @author Andy Gibson
 * 
 */
public class InMemoryIntegerProviderTest extends IntegerDataProviderTest {

	private static final long serialVersionUID = 1L;
	
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
