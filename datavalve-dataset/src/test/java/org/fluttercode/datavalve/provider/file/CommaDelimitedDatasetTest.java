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

package org.fluttercode.datavalve.provider.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.fluttercode.datavalve.dataset.Dataset;
import org.fluttercode.datavalve.dataset.ObjectDataset;
import org.fluttercode.datavalve.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * @author Andy Gibson
 * 
 */
public class CommaDelimitedDatasetTest extends
		AbstractObjectDatasetJUnitTest<PhoneEntry> {

	private static final long serialVersionUID = 1L;
	
	private String baseDir;
	private String fileName;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		generateFile();
	}

	private void generateFile() {
		
		baseDir = System.getProperty("java.io.tmpdir") + "CsvDsTest\\";
		fileName = baseDir+"csvFile.csv";
		File base = new File(baseDir);
		base.mkdir();

		FileOutputStream fs = null;

		try {
			fs = new FileOutputStream(new File(fileName));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fs));
			for (int i = 0; i < 100; i++) {
				writer.write(Integer.toString(i + 1));
				writer.write(",");
				writer.write(getDataFactory().getFirstName());
				writer.write(",");
				writer.write(getDataFactory().getLastName());
				writer.write(",");
				writer.write(getDataFactory().getNumberText(10));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		deleteFiles();
	}

	private void deleteFiles() {
		new File(fileName).delete();
		new File(baseDir).delete();
	}

	@Override
	public ObjectDataset<PhoneEntry> buildObjectDataset() {
		CommaDelimitedProvider<PhoneEntry> provider = new CommaDelimitedProvider<PhoneEntry>(fileName) {
			private static final long serialVersionUID = 2L;
			@Override
			protected PhoneEntry doCreateObjectFromColumns(String[] columns) {
				return new PhoneEntry(Integer.valueOf(columns[0]),columns[1],columns[2],columns[3]);
			}			
		};
		return new Dataset<PhoneEntry,CommaDelimitedProvider<PhoneEntry>>(provider);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

}
