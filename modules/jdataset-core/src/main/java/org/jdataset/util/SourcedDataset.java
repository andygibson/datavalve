package org.jdataset.util;

import java.util.List;

import org.jdataset.AbstractDataset;
import org.jdataset.ObjectDataset;
import org.jdataset.Paginator;

/**
 * Provides a mechanism for having multiple {@link ObjectDataset} instances
 * references the same core data to allow for 2 different 'cursors' to the same
 * data. This can be especially useful with in-memory datasets that you want to
 * display in multiple places. This way, you can have one instance of the data
 * and multiple readers of the data. You can even share the dataset in the
 * application scope since the read operations on the wrapped dataset are thread
 * safe.
 * 
 * @author Andy Gibson
 * 
 * @param <T> Type of object this dataset represents
 */
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

	protected List<T> fetchResults(Paginator paginator) {
		List<T> results = null;
		synchronized (dataset) {
			dataset.setMaxRows(getMaxRows());
			dataset.setFirstResult(getFirstResult());
			results = dataset.getResultList();
			nextAvailable = dataset.isNextAvailable();
		}
		return results;
	}

	public boolean isNextAvailable() {
		if (nextAvailable == null) {
			getResultList();
		}
		return nextAvailable;
	}

	@Override
	public void invalidateResults() {
		super.invalidateResults();
		nextAvailable = null;
	}

}
