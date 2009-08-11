package org.jdataset.util;

import java.util.List;

import org.jdataset.AbstractDataset;
import org.jdataset.ObjectDataset;

public class SourcedDataset<T> extends AbstractDataset<T> {

	private final ObjectDataset<T> dataset;
	private Boolean nextAvailable;
	
	public SourcedDataset(ObjectDataset<T> dataset) {
		super();
		this.dataset = dataset;
	}

	@Override
	protected Integer fetchResultCount() {
		Integer result = null;
		synchronized (dataset) {
			result = dataset.getResultCount();
		}
		return result;
	}

	@Override
	protected List<T> fetchResults() {
		List<T> results = null;
		synchronized (dataset) {
			dataset.setMaxRows(getMaxRows());
			dataset.setFirstResult(getFirstResult());
			results = dataset.getResults();
			nextAvailable = dataset.isNextAvailable();
		}
		return results;
	}

	public boolean isNextAvailable() {
		if (nextAvailable == null) {
			getResults();
		}
		return nextAvailable;
	}
	
	@Override
	public void invalidateResults() {
		super.invalidateResults();
		nextAvailable = null;
	}
	

}
