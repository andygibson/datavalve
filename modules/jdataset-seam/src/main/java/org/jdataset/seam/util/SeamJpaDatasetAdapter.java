package org.jdataset.seam.util;

import java.util.List;

import org.jdataset.seam.SeamJpaDataset;

/**
 * This is a utility class that subclasses the SeamJpaDataset and adds on
 * methods so the interface is the same as the default Seam entity query
 * interface. This class lets you slowly migrate to JDataset by replacing the
 * entity queries with this class and later changing it to a plain
 * SeamJpaDataset once your user interface has migrated to the slightly
 * different JDataset interface.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of entity this dataset returns
 */
public class SeamJpaDatasetAdapter<T> extends SeamJpaDataset<T> {

	public List<T> getResultList() {
		// TODO Auto-generated method stub
		return super.getResultList();
	}

	public int getActivePage() {
		return super.getPage();
	}

	public boolean getPreviousExists() {
		return super.isPreviousAvailable();
	}

	public boolean getNextExists() {
		return super.isNextAvailable();
	}
}
