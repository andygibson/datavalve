package org.jdataset.text;

import java.util.List;

public class PhoneTextFile extends CommaDelimitedDataset<PhoneEntry> {

	public PhoneTextFile(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PhoneEntry createObjectFromColumns(String[] columns) {

		return new PhoneEntry(Integer.valueOf(columns[0]), columns[1],
				columns[2], columns[3]);
	}

}
