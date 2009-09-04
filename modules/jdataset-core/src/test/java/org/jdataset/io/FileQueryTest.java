package org.jdataset.io;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jdataset.IObjectDataset;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;
import org.jdataset.util.GenericDataset;

public class FileQueryTest extends AbstractObjectDatasetJUnitTest<File> {

	private static final long serialVersionUID = 1L;
	
	private String baseDir;
	private static final int FILE_COUNT = 20;
	private static final int DIR_COUNT = 20;
	
	private class FileDataset extends GenericDataset<File, FileDataProvider> {

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
			fs.write(123);
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

		qry.setMaxRows(0);
		assertEquals(1, qry.getPageCount());

		try {
			qry.setMaxRows(-1);
			fail("setMaxRows(-1) should have thrown an exception!");
		} catch (IllegalArgumentException expected) {
			// no problem
		}

	}

	@Override
	public IObjectDataset<File> buildObjectDataset() {
		return buildFileDataset(true);
	}
	
	public FileDataset buildFileDataset(boolean includeDirs) {
		FileDataset ds = new FileDataset(new FileDataProvider(baseDir));
		ds.getProvider().setIncludeDirectories(includeDirs);
		return ds;
	}

	@Override
	public int getDataRowCount() {
		return FILE_COUNT + DIR_COUNT;
	}
}
