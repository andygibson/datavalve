package org.jdataset.util;

public abstract class CommaDelimitedProvider<T> extends TextFileProvider<T>{

	public CommaDelimitedProvider(String filename) {
		super(filename);
	}
	
	
	@Override
	protected T createObjectFromLine(String line) {
		String[] columns = line.split(",");
		return createObjectFromColumns(columns);
	}

	protected abstract T createObjectFromColumns(String[] columns);
}
