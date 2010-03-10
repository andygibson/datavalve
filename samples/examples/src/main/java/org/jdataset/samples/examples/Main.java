package org.jdataset.samples.examples;

import java.util.List;

import org.jdataset.DataProvider;
import org.jdataset.Paginator;
import org.jdataset.impl.DefaultPaginator;

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