/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.examples;

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
		InMemoryIntegerProvider provider = new InMemoryIntegerProvider();		
		return new Dataset<Integer,InMemoryIntegerProvider>(provider);
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
