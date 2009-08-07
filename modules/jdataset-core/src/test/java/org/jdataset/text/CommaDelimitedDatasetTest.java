package org.jdataset.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jdataset.ObjectDataset;
import org.jdataset.testing.TestDataFactory;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;

public class CommaDelimitedDatasetTest extends
		AbstractObjectDatasetJUnitTest<PhoneEntry> {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		deleteFiles();
	}

	private void deleteFiles() {
		new File(fileName).delete();
		new File(baseDir).delete();
	}

	@Override
	public ObjectDataset<PhoneEntry> buildObjectDataset() {
		ObjectDataset<PhoneEntry> result = new CommaDelimitedDataset<PhoneEntry>(fileName) {
			private static final long serialVersionUID = 2L;
			@Override
			protected PhoneEntry createObjectFromColumns(String[] columns) {
				return new PhoneEntry(Integer.valueOf(columns[0]),columns[1],columns[2],columns[3]);
			}			
		};
		return result;
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

}
