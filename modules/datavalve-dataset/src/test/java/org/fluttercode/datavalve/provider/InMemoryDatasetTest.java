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

package org.fluttercode.datavalve.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.fluttercode.datavalve.dataset.Dataset;
import org.fluttercode.datavalve.dataset.ObjectDataset;
import org.fluttercode.datavalve.provider.InMemoryDataProvider;
import org.fluttercode.datavalve.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * @author Andy Gibson
 * 
 */
public class InMemoryDatasetTest extends
		AbstractObjectDatasetJUnitTest<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(InMemoryDatasetTest.class
			.getName());

	public ObjectDataset<Integer> buildTestDataset() {

		return buildTestDataset(100);
	}

	public ObjectDataset<Integer> buildTestDataset(final int count) {

		InMemoryDataProvider<Integer> ds = new InMemoryDataProvider<Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Integer> fetchBackingData() {
				List<Integer> res = new ArrayList<Integer>();
				for (int i = 0; i < count; i++) {
					res.add(i);
				}
				return res;
			}
		};

		return new Dataset<Integer, InMemoryDataProvider<Integer>>(
				ds);
	}

	public void testGetResultCount() {
		ObjectDataset<Integer> ds = buildTestDataset();
		int res = ds.getResultCount();
		assertEquals(100, res);
	}

	public void testGetResultsUnbound() {
		ObjectDataset<Integer> ds = buildTestDataset();
		List<Integer> res = ds.getResultList();

		assertNotNull(res);
		assertEquals(100, res.size());
		for (int i = 0; i < 10; i++) {
			assertEquals(i, res.get(i).intValue());
		}
	}

	/**
	 * Tests that ordering does not change when just the order is
	 * changed
	 */
	public void testGetResultsUnboundOrdering() {
		ObjectDataset<Integer> ds = buildTestDataset();
		List<Integer> original = ds.getResultList();
		ds.setOrderAscending(false);
		List<Integer> list1 = ds.getResultList();
		ds.setOrderAscending(true);
		List<Integer> list2 = ds.getResultList();

		assertNotNull(original);
		assertNotNull(list1);
		assertNotNull(list2);
		assertEquals(100, original.size());
		assertEquals(100, list1.size());
		assertEquals(100, list2.size());
		
		for (int i = 0; i < 10; i++) {
			assertEquals(i, original.get(i).intValue());
			assertEquals(original.get(i).intValue(), list1.get(i).intValue());
			assertEquals(original.get(i).intValue(), list2.get(i).intValue());
		}
	}

	public void testGetResultsBound() {
		ObjectDataset<Integer> ds = buildTestDataset();
		ds.setMaxRows(12);
		List<Integer> res = ds.getResultList();

		assertNotNull(res);
		assertEquals(12, res.size());
		for (int i = 0; i < 12; i++) {
			assertEquals(i, res.get(i).intValue());
		}
	}

	public void testLast() {
		ObjectDataset<Integer> ds = buildTestDataset();
		ds.setMaxRows(10);
		ds.last();
		assertEquals(10, ds.getPageCount());
		int firstResult = ds.getFirstResult();
		log.fine("First result = " + firstResult);
		assertEquals(90, firstResult);
	}

	public void testLastWithFractionalCount() {
		ObjectDataset<Integer> ds = buildTestDataset(95);
		ds.setMaxRows(10);
		ds.last();
		Integer firstResult = ds.getFirstResult();
		assertNotNull(firstResult);
		assertEquals(90, firstResult.longValue());
	}

	public void testLastWithNoPaging() {
		ObjectDataset<Integer> ds = buildTestDataset(5);
		ds.setMaxRows(10);
		ds.last();
		int firstResult = ds.getFirstResult();
		log.fine("First result = " + firstResult);
		assertEquals(0, firstResult);
	}

	public void testPaginationPreviousAllResults() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		assertEquals(false, ds.isPreviousAvailable());
	}

	public void testPaginationNextAllResults() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		assertEquals(false, ds.isNextAvailable());
	}

	public void testPaginationPreviousPaged() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		ds.setMaxRows(10);
		assertEquals(false, ds.isPreviousAvailable());
	}

	public void testPaginationNextPaged() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		ds.setMaxRows(10);
		assertEquals(true, ds.isNextAvailable());
	}

	public void testPaginationNextNoRead() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		ds.setMaxRows(10);
		ds.next();
		assertEquals(true, ds.isNextAvailable());
		assertEquals(true, ds.isPreviousAvailable());

	}

	public void testPaginationPreviousNoRead() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		ds.setMaxRows(10);
		ds.previous();
		assertEquals(0, ds.getFirstResult());
		assertEquals(1, ds.getPage());
		assertEquals(true, ds.isNextAvailable());
		assertEquals(false, ds.isPreviousAvailable());
	}

	public void testPaginationLastNoRead() {
		ObjectDataset<Integer> ds = buildTestDataset(100);
		ds.setMaxRows(10);
		ds.last();
		assertEquals(false, ds.isNextAvailable());
		assertEquals(true, ds.isPreviousAvailable());
	}

	public void testSmalldataset() {
		ObjectDataset<Integer> ds = buildTestDataset(6);
		ds.setMaxRows(10);
		assertEquals(false, ds.isNextAvailable());
		assertEquals(false, ds.isPreviousAvailable());
		ds.next();
		assertEquals(6, ds.getResultCount().intValue());
		assertEquals(false, ds.isNextAvailable());
		assertEquals(false, ds.isPreviousAvailable());

	}

	@Override
	public ObjectDataset<Integer> buildObjectDataset() {
		return buildTestDataset(100);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

}
