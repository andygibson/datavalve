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

package org.fluttercode.spigot.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.provider.file.CommaDelimitedProvider;
import org.fluttercode.spigot.testing.TestDataFactory;
import org.fluttercode.spigot.testing.junit.AbstractObjectDatasetJUnitTest;

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
				writer.write(TestDataFactory.getFirstName());
				writer.write(",");
				writer.write(TestDataFactory.getLastName());
				writer.write(",");
				writer.write(TestDataFactory.getNumberText(10));
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
			protected PhoneEntry createObjectFromColumns(String[] columns) {
				return new PhoneEntry(Integer.valueOf(columns[0]),columns[1],columns[2],columns[3]);
			}			
		};
		return new Dataset<PhoneEntry>(provider);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

}
