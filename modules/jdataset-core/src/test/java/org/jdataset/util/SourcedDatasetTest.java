package org.jdataset.util;


import java.util.ArrayList;
import java.util.List;

import org.jdataset.BackedDataset;
import org.jdataset.ObjectDataset;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;
import org.jdataset.text.PhoneEntry;

public class SourcedDatasetTest extends
		AbstractObjectDatasetJUnitTest<PhoneEntry> {
	
	private BackedDataset<PhoneEntry> dataset;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		final List<PhoneEntry> list = new ArrayList<PhoneEntry>();
		for (int i = 0;i < 100;i++) {
			list.add(new PhoneEntry(i));
		}
		dataset = new BackedDataset<PhoneEntry>() {
			
			@Override
			protected List<PhoneEntry> fetchBackingData() {
				return list;
			}
		};
	}

	@Override
	public ObjectDataset<PhoneEntry> buildObjectDataset() {
		return new SourcedDataset<PhoneEntry>(dataset);
	}

	@Override
	public int getDataRowCount() {
		// TODO Auto-generated method stub
		return 100;
	}
	
	
}
