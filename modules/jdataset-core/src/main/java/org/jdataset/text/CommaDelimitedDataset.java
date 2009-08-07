package org.jdataset.text;

public abstract class CommaDelimitedDataset<T> extends TextFileDataset<T> {	
	
	private static final long serialVersionUID = 1L;

	public CommaDelimitedDataset(String fileName) {
		super(fileName);
	}

	@Override
	protected T createObjectFromLine(String line) {		
		String[] columns = line.split(",");
		return createObjectFromColumns(columns);
	}

	protected abstract T createObjectFromColumns(String[] columns);
}
