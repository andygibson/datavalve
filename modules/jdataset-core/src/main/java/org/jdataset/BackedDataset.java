package org.jdataset;

import java.util.Collections;
import java.util.List;

import org.jdataset.util.LazyList;

/**
 * <p>
 * Implementation of an {@link ObjectDataset} that holds its own data list that
 * is used to back the dataset. Introduces a new abstract method to be
 * overridden that allows subclasses to return the <code>List</code> that backs
 * this dataset.
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
 * {@link BackedDataset#fetchBackingData()} method <b>must</b> return all values
 * in the whole dataset. It is only from examining the complete list that we
 * determine whether or not paging is available.
 * </p>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object this dataset contains.
 */
public abstract class BackedDataset<T> extends AbstractDataset<T> {

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
	@Override
	protected final Integer fetchResultCount() {
		// make this final since it uses the backing data to get the count
		return Integer.valueOf(getBackingData().size());
	}

	/**
	 * Lazy loads the data that backs the dataset result set from the
	 * <code>fetchBackingData()</code> method.
	 * 
	 * @see BackedDataset#fetchBackingData()
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
	@Override
	protected final List<T> fetchResults() {
		// make this method final since

		// make sure we fetch the data
		getBackingData();

		int startPos = getFirstResult();
		if (startPos > backingData.size()) {
			startPos = backingData.size();
		}

		int endPos = getMaxRows() == 0 ? backingData.size() : startPos
				+ getMaxRows();
		if (endPos >= backingData.size()) {
			endPos = backingData.size();
		}

		return backingData.subList(startPos, endPos);
	}

	/**
	 * Overridden to also invalidate the backing data values used to back this
	 * list. We do it on the invalidate info since this is called when the
	 * underlying structure may have changed.
	 * 
	 * @see org.jdataset.AbstractDataset#invalidateResultInfo()
	 */
	@Override
	public void invalidateResultInfo() {
		super.invalidateResultInfo();
		// also invalidate the backing data
		backingData = null;
	}

	public final boolean isNextAvailable() {
		// since we are using an in-memory list, we can just examine that to see
		// if there are more results available.
		return getFirstResult() + getResults().size() < fetchResultCount();
	}

}
