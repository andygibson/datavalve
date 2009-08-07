package org.datagen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdataset.testing.TestDataFactory;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileWriter fw = new FileWriter(new File("c:\\testData100000.csv"));
			for (int i = 0; i < 100000; i++) {
				fw.write(Integer.toString(i));
				fw.write(',');
				fw.write(TestDataFactory.getFirstName());
				fw.write(',');
				fw.write(TestDataFactory.getLastName());
				fw.write(',');
				fw.write(TestDataFactory.getNumberText(10));
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
