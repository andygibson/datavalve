package org.jdataset.impl;

/**
 * This dataset extends the {@link TextFileDataset} dataset to treat each line
 * as list of comma delimited values. Each line is processed as defined in the
 * <code>TestFileDataset</code> and for each line, we call the
 * {@link #createObjectFromColumns(String[])} method to map the list of columns
 * to properties on the object that is returned.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            the type of object this dataset holds
 */
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
