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

package org.jdataset.io;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.jdataset.dataset.GenericProviderDataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.FileDataProvider;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * @author Andy Gibson
 * 
 */
public class FileQueryTest extends AbstractObjectDatasetJUnitTest<File> {

	private static final long serialVersionUID = 1L;

	private String baseDir;
	private static final int FILE_COUNT = 20;
	private static final int DIR_COUNT = 20;
	private static final Random random = new Random();

	private int getRandomValue(int min, int max) {
		return min + random.nextInt(max - min);
	}

	private class FileDataset extends
			GenericProviderDataset<File, FileDataProvider> {

		private static final long serialVersionUID = 1L;

		public FileDataset(FileDataProvider provider) {
			super(provider);
		}

		@Override
		public void invalidateResultInfo() {
			super.invalidateResultInfo();
			getProvider().invalidateData();
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		baseDir = System.getProperty("java.io.tmpdir") + "FileDsTest\\";

		File base = new File(baseDir);
		base.mkdir();
		FileOutputStream fs = null;

		for (int i = 0; i < FILE_COUNT; i++) {
			fs = new FileOutputStream(new File(baseDir + "file" + i + ".txt"));
			int count = getRandomValue(5, 100);
			for (int s = 0; s < count; s++) {
				fs.write(123);
			}
			fs.close();
		}

		// create directories
		for (int i = 0; i < DIR_COUNT; i++) {
			File file = new File(baseDir + "dir" + i);
			file.mkdir();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		deleteDir(baseDir);

	}

	protected void deleteDir(String path) {
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				deleteDir(file.getAbsolutePath());
			}
			file.delete();
		}
		dir.delete();
	}

	public void testFileDataQueryStringIncludingDirs() {
		FileDataset qry = buildFileDataset(true);
		List<File> res = qry.getResultList();
		assertEquals(40, res.size());
	}

	public void testFileDataQueryRecordCountIncludingDirs() {
		FileDataset qry = buildFileDataset(true);
		long count = qry.getResultCount();
		assertEquals(40, count);
	}

	public void testFileDataQueryStringExcludingDirs() {
		FileDataset qry = buildFileDataset(false);
		List<File> res = qry.getResultList();
		assertEquals(20, res.size());
	}

	public void testFileDataQueryRecordCountExcludingDirs() {
		FileDataset qry = buildFileDataset(false);
		long count = qry.getResultCount();
		assertEquals(20, count);
	}

	public void testIncDirChange() {
		// test changing include dirs invalidates the results
		FileDataset qry = buildFileDataset(false);
		assertEquals(20, qry.getResultCount().intValue());
		qry.getProvider().setIncludeDirectories(true);
		qry.invalidateResultInfo();

		assertEquals(40, qry.getResultCount().intValue());
		qry.getProvider().setIncludeDirectories(false);
		qry.invalidateResultInfo();
		assertEquals(20, qry.getResultCount().intValue());
	}

	public void testPagination() {
		FileDataset qry = buildFileDataset(false);
		qry.setMaxRows(10);
		assertEquals(1, qry.getPage());
		qry.getProvider().setIncludeDirectories(true);

		assertEquals(40, qry.getResultCount().intValue());
		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.next();
		assertEquals(2, qry.getPage());
		assertEquals(true, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.next();
		assertEquals(3, qry.getPage());
		assertEquals(true, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.next();
		assertEquals(4, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.next();
		assertEquals(4, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.previous();
		assertEquals(3, qry.getPage());
		assertEquals(true, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

	}

	public void testPageCount() {
		FileDataset qry = buildFileDataset(false);

		assertEquals(20, qry.getResultCount().intValue());

		qry.setMaxRows(10);
		assertEquals(2, qry.getPageCount());

		qry.setMaxRows(13);
		assertEquals(2, qry.getPageCount());

		qry.setMaxRows(7);
		assertEquals(3, qry.getPageCount());

		qry.setMaxRows(5);
		assertEquals(4, qry.getPageCount());

		qry.setMaxRows(4);
		assertEquals(5, qry.getPageCount());

		qry.setMaxRows(2);
		assertEquals(10, qry.getPageCount());

		qry.setMaxRows(1);
		assertEquals(20, qry.getPageCount());

		qry.setMaxRows(null);
		assertEquals(1, qry.getPageCount());

		try {
			qry.setMaxRows(-1);
			fail("setMaxRows(-1) should have thrown an exception!");
		} catch (IllegalArgumentException expected) {
			// no problem
		}

	}

	@Override
	public ObjectDataset<File> buildObjectDataset() {
		return buildFileDataset(true);
	}

	public FileDataset buildFileDataset(boolean includeDirs) {
		FileDataset ds = new FileDataset(new FileDataProvider(baseDir));
		ds.getProvider().setIncludeDirectories(includeDirs);
		ds.getProvider().getOrderKeyMap().put("name", new Comparator<File>() {

			public int compare(File o1, File o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		ds.getProvider().getOrderKeyMap().put("size", new Comparator<File>() {

			public int compare(File o1, File o2) {
				return (int) (o1.length() - o2.length());
			}
		});
		return ds;
	}

	@Override
	public int getDataRowCount() {
		return FILE_COUNT + DIR_COUNT;
	}

	public void testOrderingSizeAsc() {
		FileDataset qry = buildFileDataset(false);
		qry.setOrderKey("size");
		qry.setOrderAscending(true);
		List<File> results = qry.getResultList();
		long size = 0;
		for (File f : results) {
			assertTrue(size <= f.length());
			size = f.length();
		}
	}

	public void checkListOrderedNameAsc(List<File> list) {
		String value = null;
		for (File f : list) {
			if (value != null) {
				assertTrue(value.compareToIgnoreCase(f.getName()) < 0);
			}
			value = f.getName();
		}
	}

	public void checkListOrderedNameDesc(List<File> list) {
		String value = null;
		for (File f : list) {
			if (value != null) {
				assertTrue(value.compareToIgnoreCase(f.getName()) > 0);
			}
			value = f.getName();
		}
	}

	public void checkListOrderedSizeDesc(List<File> list) {
		long size = 999999;
		for (File f : list) {
			assertTrue(size >= f.length());
			size = f.length();
		}
	}

	public void checkListOrderedSizeAsc(List<File> list) {
		long size = 0;
		for (File f : list) {
			assertTrue(size <= f.length());
			size = f.length();
		}
	}

	public void testOrdering() {
		FileDataset qry = buildFileDataset(false);
		qry.setOrderKey("size");

		qry.setOrderAscending(false);
		List<File> results = qry.getResultList();
		checkListOrderedSizeDesc(results);

		qry.setOrderAscending(true);
		results = qry.getResultList();
		checkListOrderedSizeAsc(results);
		//
		qry.setOrderAscending(false);
		qry.setOrderKey("name");
		results = qry.getResultList();
		checkListOrderedNameDesc(results);

		// check that order is correct when we change the key
		qry.setOrderKey("size");
		results = qry.getResultList();
		checkListOrderedSizeDesc(results);

	}

	@Override
	public boolean includeSerializationTest() {
		return false;
	}
}
