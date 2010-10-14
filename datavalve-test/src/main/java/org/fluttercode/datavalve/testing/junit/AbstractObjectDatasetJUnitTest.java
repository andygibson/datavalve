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

package org.fluttercode.datavalve.testing.junit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.fluttercode.datafactory.impl.DataFactory;
import org.fluttercode.datavalve.dataset.ObjectDataset;

/**
 * 
 * This class is an abstract class that implements a set of tests against an
 * {@link ObjectDataset} interface. You just need to subclass this and provide
 * an implementation and the number of records in the dataset and this will test the
 * pagination handling and other features of the dataset.
 * </p>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public abstract class AbstractObjectDatasetJUnitTest<T> extends TestCase
		implements Serializable {

	private static final long serialVersionUID = 1L;
	private static DataFactory dataFactory = new DataFactory();

	public abstract ObjectDataset<T> buildObjectDataset();

	public abstract int getDataRowCount();

	public static DataFactory getDataFactory() {
		return dataFactory;
	}
	
	/**
	 * Test that the data row count is at least 30 (3 pages * 10);
	 */
	public void testRowCount() {
		boolean hasEnoughRows = getDataRowCount() >= 30;
		if (!hasEnoughRows) {
			throw new RuntimeException(
					"you must have at least 30 rows of data to use the built-in Object dataset tests");
		}
	}

	public void testBuilder() {
		ObjectDataset<T> ds = buildObjectDataset();
		assertNotNull(ds);
	}

	/**
	 * Test isPreviousAvailable as soon as we create a dataset with no paging
	 */
	public void testPreviousNoRead() {
		ObjectDataset<T> ds = buildObjectDataset();
		assertEquals(false, ds.isPreviousAvailable());
	}

	/**
	 * Test isNextAvailable as soon as we create a dataset with no paging
	 */
	public void testNextNoRead() {
		ObjectDataset<T> ds = buildObjectDataset();
		// this should be false since paging is not enabled by default, hence it
		// is all one big page
		assertEquals(false, ds.isNextAvailable());
	}

	/**
	 * Test isPreviousAvailable after performing a result set read with pagining
	 */
	public void testPreviousPagedNoRead() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		assertEquals(false, ds.isPreviousAvailable());
	}

	/**
	 * Test isNextAvailable after performing a result set read with pagining
	 */
	public void testNextPagedNoRead() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		assertEquals(true, ds.isNextAvailable());
	}

	/**
	 * Check the result count matches the data row count provided by the
	 * developer
	 */
	public void testResultCount() {
		ObjectDataset<T> ds = buildObjectDataset();
		assertEquals(getDataRowCount(), ds.getResultCount().intValue());
	}

	/**
	 * Check the number of results returned matches the data row count provided
	 * by the developer
	 */
	public void testActualResultCount() {
		ObjectDataset<T> ds = buildObjectDataset();
		List<T> results = ds.getResultList();

		assertEquals(ds.getResultCount().intValue(), results.size());
		assertEquals(getDataRowCount(), results.size());
	}

	public void testNextDifferentResults() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		List<T> oldResults = ds.getResultList();
		assertEquals(oldResults.size(), ds.getMaxRows().intValue());
		ds.next();
		assertEquals(10, ds.getFirstResult());
		List<T> results = ds.getResultList();
		// check results are not the same and we have a different result set.
		for (int i = 0; i < 10; i++) {
			assertNotSame(oldResults.get(i), results.get(i));
		}
	}

	public void testNullingMaxRows() {
		// test we can set the maxRows value to null after it has been set and
		// the query will come back correct
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		List<T> oldResults = ds.getResultList();
		assertEquals(oldResults.size(), ds.getMaxRows().intValue());
		ds.setMaxRows(null);
		List<T> newResults = ds.getResultList();
		assertNotSame(oldResults.size(), newResults.size());
		assertEquals(getDataRowCount(), newResults.size());
	}

	/**
	 * Tests the expected result count by fetching the results till we run out
	 */
	public void testResultCountByFetching() {
		int count = 0;
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		List<T> results = ds.getResultList();
		count += results.size();
		while (ds.isNextAvailable()) {
			ds.next();
			results = ds.getResultList();
			count += results.size();
		}
		assertEquals(getDataRowCount(), count);
		assertEquals(getDataRowCount(), ds.getResultCount().intValue());
	}

	/**
	 * Check the multi page flag is set when we have multiple pages
	 */
	public void testIsMultiPageFlagPositive() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10); // we should have 30+ rows
		assertEquals(true, ds.isMultiPage());
	}

	/**
	 * Check the multi page flag is not set when we don't have multiple pages.
	 */
	public void testIsMultiPageFlagNegative() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(ds.getResultCount());
		assertEquals(false, ds.isMultiPage());
	}

	/**
	 * Check that the page count is correct for different page sizes and also
	 * when maxResult = 0 and we have no paging (check 1 is returned)
	 */
	public void testPageCountSize() {
		ObjectDataset<T> ds = buildObjectDataset();

		int rowCount = getDataRowCount();
		for (int i = 1; i < 30; i++) {
			ds.setMaxRows(i); // we should have 30+ rows
			int pages = ds.getPageCount();
			assertTrue(pages * ds.getMaxRows() >= rowCount);
			assertTrue((pages * ds.getMaxRows()) - ds.getMaxRows() < rowCount);
		}

	}

	/**
	 * Check that the page numbers increase when doing next
	 */
	public void testGetPageNext() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		assertEquals(1, ds.getPage());
		ds.next();
		assertEquals(2, ds.getPage());
		ds.next();
		assertEquals(3, ds.getPage());
	}

	/**
	 * Check that the page numbers decrease when doing previous
	 */
	public void testGetPagePrevious() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		ds.setFirstResult(20);
		assertEquals(3, ds.getPage());
		ds.previous();
		assertEquals(2, ds.getPage());
		ds.previous();
		assertEquals(1, ds.getPage());
	}

	/**
	 * Check that the page numbers increase when doing next/previous
	 */
	public void testGetPageNoPagination() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		assertEquals(1, ds.getPage());
		ds.next();
		assertEquals(2, ds.getPage());
		ds.next();
		assertEquals(3, ds.getPage());
		ds.previous();
		assertEquals(2, ds.getPage());
		ds.previous();
		assertEquals(1, ds.getPage());
		ds.previous();
		assertEquals(1, ds.getPage());
	}

	/**
	 * Tests what happens when firstResult * pageSize > result count. An empty
	 * list should be returned.
	 */
	public void testReadingBeyondResults() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setFirstResult(getDataRowCount() + 10);
		ds.setMaxRows(10);
		List<T> results = ds.getResultList();
		assertEquals(0, results.size());
		assertEquals(false, ds.isNextAvailable());
		assertEquals(true, ds.isPreviousAvailable());
	}

	/**
	 * Tests the next flag without reading the results.
	 */
	public void testNextFlagsNoRead() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);

		// assume we have tested page count
		int pageCount = ds.getPageCount();

		for (int i = 0; i < pageCount - 1; i++) {
			assertEquals(true, ds.isNextAvailable());
			ds.next();
		}
		assertEquals(false, ds.isNextAvailable());
	}

	/**
	 * Tests the previous flag without reading the results.
	 */
	public void testPreviousFlagsNoRead() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);

		// assume we have tested page count
		int pageCount = ds.getPageCount();

		assertEquals(false, ds.isPreviousAvailable());
		for (int i = 0; i < pageCount; i++) {
			ds.next();
			assertEquals(true, ds.isPreviousAvailable());
		}
	}

	/**
	 * Test the for each iterator without paging
	 */
	public void testForEachIteratorNonPaged() {
		ObjectDataset<T> ds = buildObjectDataset();
		int count = 0;
		Object old = null;
		for (T object : ds) {
			assertNotNull(object);
			assertNotSame(old, object);
			old = object;
			count++;
		}
		assertEquals(getDataRowCount(), count);
	}

	/**
	 * Test the for each iterator with paging
	 */
	public void testForEachIteratorPaged() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		int count = 0;
		Object old = null;
		for (T object : ds) {
			assertNotNull(object);
			assertNotSame(old, object);
			old = object;
			count++;
		}
		assertEquals(getDataRowCount(), count);
	}

	/**
	 * Test the for each iterator without paging and an offset at the start
	 */
	public void testForEachIteratorNonPagedStartingOffset() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setFirstResult(22);
		ds.getResultList();
		int count = 0;
		Object old = null;
		for (T object : ds) {
			assertNotNull(object);
			assertNotSame(old, object);
			old = object;
			count++;
		}
		assertEquals(getDataRowCount(), count);
	}

	/**
	 * Test the for each iterator with paging and with an offset at the start
	 */
	public void testForEachIteratorPagedStartingOffset() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setMaxRows(10);
		ds.setFirstResult(22);
		ds.getResultList();

		int count = 0;
		Object old = null;
		for (T object : ds) {
			assertNotNull(object);
			assertNotSame(old, object);
			old = object;
			count++;
		}
		assertEquals(getDataRowCount(), count);
	}

	/**
	 * Test the iterator manually
	 */
	public void testManualIteratorPaged() {
		ObjectDataset<T> ds = buildObjectDataset();
		int count = 0;
		for (Iterator<T> iter = ds.iterator(); iter.hasNext();) {
			T object = iter.next();
			assertNotNull(object);
			count++;
		}
		assertEquals(getDataRowCount(), count);
	}

	/**
	 * Test that iterator.remove throws an exception
	 */
	public void testRemoveException() {
		ObjectDataset<T> ds = buildObjectDataset();
		try {
			for (Iterator<T> iter = ds.iterator(); iter.hasNext();) {
				iter.remove();
				fail("iter.remove failed to throw an exception");
			}
		} catch (UnsupportedOperationException ex) {
			// eat exception, we expected it
		}

	}

	/**
	 * Set of tests to verify that when changing the order key through the and
	 * changeOrderKey methods, the ascending flag is being toggled correctly
	 */
	public void testOrderKeyToggle() {
		ObjectDataset<T> ds = buildObjectDataset();

		assertNull(ds.getOrderKey());

		ds.changeOrderKey("ABC");
		ds.changeOrderKey("ABC");
		assertEquals("ABC", ds.getOrderKey());
		assertFalse(ds.isOrderAscending());

		ds.changeOrderKey("ABC");
		assertTrue(ds.isOrderAscending());

		ds.changeOrderKey("DEF");
		assertTrue(ds.isOrderAscending());

		ds.changeOrderKey("DEF");
		assertFalse(ds.isOrderAscending());

		ds.setOrderKey("XYZ");
		assertFalse(ds.isOrderAscending());
	}

	/**
	 * Test the isAscending flag defaults to true when a dataset is created
	 */
	public void testAscendingDefault() {
		ObjectDataset<T> ds = buildObjectDataset();
		assertTrue(ds.isOrderAscending());
	}

	/**
	 * Test the isAscending flag is unaltered by setting the orderkey
	 */
	public void testAscendingTrueAfterSetOrderKey() {
		ObjectDataset<T> ds = buildObjectDataset();
		ds.setOrderKey("ABC");
		assertTrue(ds.isOrderAscending());

		ds.setOrderAscending(false);
		ds.setOrderKey("ABC");
		assertFalse(ds.isOrderAscending());

		ds.setOrderAscending(true);
		ds.setOrderKey("ABC");
		assertTrue(ds.isOrderAscending());

		ds.setOrderAscending(false);
		ds.setOrderKey("DEF");
		assertFalse(ds.isOrderAscending());

		ds.setOrderAscending(true);
		ds.setOrderKey("XYZ");
		assertTrue(ds.isOrderAscending());
	}

	protected void performSerializationTest(ObjectDataset<T> dataset) {
		ByteArrayOutputStream bas = new ByteArrayOutputStream(4000);
		ObjectOutputStream oos;

		try {
			oos = new ObjectOutputStream(bas);
			try {
				oos.writeObject(dataset);
			} catch (NotSerializableException e) {
				fail("Dataset is not serializable : " + e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void testSerialization() {
		if (includeSerializationTest()) {
			ObjectDataset<T> ds = buildObjectDataset();
			performSerializationTest(ds);
		}
	}

	public boolean includeSerializationTest() {
		return true;
	}

}
