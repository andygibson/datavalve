/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.samples.examples;

import org.fluttercode.datavalve.dataset.Dataset;
import org.fluttercode.datavalve.dataset.ObjectDataset;
import org.fluttercode.datavalve.testing.junit.AbstractObjectDatasetJUnitTest;

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
		IntegerDataProvider provider = new IntegerDataProvider();
		return new Dataset<Integer, IntegerDataProvider>(provider);
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
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected = expected + 1;
		}
	}

	public void testResultsNonPaginated() {
		ObjectDataset<Integer> ds = buildObjectDataset();
		Integer expected = 1;
		for (Integer i : ds) {
			assertEquals(expected, i);
			expected = expected + 1;
		}
	}

}
