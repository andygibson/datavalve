package org.jdataset.text;

import org.jdataset.util.CommaDelimitedProvider;


public class PhoneTextFile extends CommaDelimitedProvider<PhoneEntry> {

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
