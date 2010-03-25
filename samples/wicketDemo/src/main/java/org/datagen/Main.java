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

package org.datagen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdataset.testing.TestDataFactory;

/**
 * @author Andy Gibson
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
			e.printStackTrace();
		}

	}
}
