package org.jdataset.text;

import org.jdataset.dataset.CommaDelimitedDataset;


public class PhoneTextFile extends CommaDelimitedDataset<PhoneEntry> {

	private static final long serialVersionUID = 1L;

	public PhoneTextFile(String fileName) {
		super(fileName);
	}

	@Override
	protected PhoneEntry createObjectFromColumns(String[] columns) {

		return new PhoneEntry(Integer.valueOf(columns[0]), columns[1],
				columns[2], columns[3]);
	}

}
