/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.examples;

import java.util.List;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.DefaultPaginator;
import org.fluttercode.spigot.Paginator;

/**
 * Main class with the main() method. Use this class to play around with the
 * demos that appear in the documentation.
 * 
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
