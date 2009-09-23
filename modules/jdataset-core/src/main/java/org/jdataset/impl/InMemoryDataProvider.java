package org.jdataset.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jdataset.ObjectDataset;
import org.jdataset.Paginator;
import org.jdataset.provider.DataProvider;
import org.jdataset.util.LazyList;

/**
 * <p>
 * Implementation of an {@link ObjectDataset} that holds its own data list in
 * memory that is used to back the dataset. Introduces a new abstract method to
 * be overridden that allows subclasses to return the <code>List</code> that
 * backs this dataset.
 * </p>
 * <p>
 * This class can be used to provide an {@link ObjectDataset} interface to any
 * data even if it is held in any kind of list. For example, you can create a
 * paginated dataset from a list of <code>File</code> objects. If the content
 * you need is too big to be held in memory, then you could use a
 * {@link LazyList} class to back the list.
 * </p>
 * <p>
 * Note that regardless of whether the dataset is paged or not, the
 * {@link InMemoryDataProvider#fetchBackingData()} method <b>must</b> return all
 * values in the whole dataset. It is only from examining the complete list that
 * we determine whether or not paging is available.
 * </p>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object this dataset contains.
 */
public abstract class InMemoryDataProvider<T> implements DataProvider<T>,
		Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> backingData;

	/**
	 * Implements the getResultCount function by fetching the backing data and
	 * returning the size of that.
	 * 
	 * @see #getBackingData()
	 * 
	 * @see org.jdataset.AbstractDataset#fetchResultCount()
	 */
	public final Integer fetchResultCount() {
		// make this final since it uses the backing data to get the count
		return Integer.valueOf(getBackingData().size());
	}

	/**
	 * Lazy loads the data that backs the dataset result set from the
	 * <code>fetchBackingData()</code> method.
	 * 
	 * @see InMemoryDataProvider#fetchBackingData()
	 */
	public final List<T> getBackingData() {
		if (backingData == null) {
			backingData = fetchBackingData();

			if (backingData == null) {
				backingData = Collections.emptyList();
			}
		}
		return backingData;
	}

	/**
	 * Method that the user must override to return the backing data for this
	 * dataset. This is the strategy method for implementing this in-memory
	 * dataset and providing the dataset with all the results that back this
	 * dataset.
	 * <p>
	 * Subclasses should override this method and return the complete list of
	 * data that this dataset contains
	 * <p>
	 * If there is too much data to load at once, consider using a
	 * {@link LazyList} to back the dataset.
	 * 
	 * @return The list of type <code>List&ltT&gt</code> containing all the data
	 *         that is used to provide data to the dataset.
	 */
	protected abstract List<T> fetchBackingData();

	/**
	 * Made final in this class because it relies on the
	 * <code>fetchBackingDataList</code> method.
	 * 
	 * @see org.jdataset.AbstractDataset#fetchResults()
	 * 
	 */
	public final List<T> fetchResults(Paginator paginator) {
		// make this method final since

		// make sure we fetch the data
		getBackingData();

		int startPos = paginator.getFirstResult();
		if (startPos > backingData.size()) {
			startPos = backingData.size();
		}

		int endPos = paginator.getMaxRows() == null ? backingData.size()
				: startPos + paginator.getMaxRows();
		if (endPos >= backingData.size()) {
			endPos = backingData.size();
		}

		List<T> results = backingData.subList(startPos, endPos);
		paginator
				.setNextAvailable(paginator.getFirstResult() + results.size() < fetchResultCount());
		return results;
	}

	public void invalidateData() {
		backingData = null;
	}
}
