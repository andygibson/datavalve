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

package org.fluttercode.spigot.samples.examples;

import java.util.List;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.DefaultPaginator;
import org.fluttercode.spigot.Paginator;

/**
 * @author Andy Gibson
 * 
 */
public class Main {

	public static void main(String[] args) {
		demoIntegerDataProvider();
	}

	/**
	 * Demos the {@link IntegerDataProvider} class from the documentation
	 */
	public static void demoIntegerDataProvider() {
		Paginator paginator = new DefaultPaginator(10);
		DataProvider<Integer> provider = new IntegerDataProvider();		

		List<Integer> results = provider.fetchResults(paginator);
		showResults(results);
	}
	public static void showResults(List<Integer> results) {
		System.out.println("Result Size = " + results.size());

		for (Integer i : results) {
			System.out.println("Value = " + i);
		}
	}
}
