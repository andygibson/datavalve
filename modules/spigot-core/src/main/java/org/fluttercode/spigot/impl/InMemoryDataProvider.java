/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;
import org.fluttercode.spigot.util.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static Logger log = LoggerFactory
			.getLogger(InMemoryDataProvider.class);

	private Map<String, Comparator<T>> orderKeyMap = new HashMap<String, Comparator<T>>();
	private Comparator<T> activeSortOrder;
	private boolean orderedAscendingFlag = true;

	private List<T> backingData;

	/**
	 * Implements the getResultCount function by fetching the backing data and
	 * returning the size of that.
	 * 
	 * @see #getBackingData()
	 * 
	 * @see org.fluttercode.spigot.AbstractDataset#fetchResultCount()
	 */
	public Integer fetchResultCount() {
		return Integer.valueOf(getBackingData().size());
	}

	/**
	 * Lazy loads the data that backs the dataset result set from the
	 * <code>fetchBackingData()</code> method.
	 * 
	 * @see InMemoryDataProvider#fetchBackingData()
	 */
	public List<T> getBackingData() {
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
	 * @see org.fluttercode.spigot.AbstractDataset#fetchResults()
	 * 
	 */
	public List<T> fetchResults(Paginator paginator) {
		// make this method final since

		// make sure we fetch the data
		getBackingData();
		// check sorting hasn't changed
		defineOrdering(paginator.getOrderKey());
		defineOrderDirection(paginator.isOrderAscending());
		sort();

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

	public Map<String, Comparator<T>> getOrderKeyMap() {
		return orderKeyMap;
	}

	protected boolean isSorted() {
		return activeSortOrder != null;
	}

	private void defineOrderDirection(boolean ascending) {
		if (ascending != orderedAscendingFlag) {
			orderedAscendingFlag = ascending;
			// flip the list
			// log.debug("Flipping list for sort order");
			// Collections.reverse(getBackingData());
		}
	}

	private void defineOrdering(String key) {
		if (key == null) {
			activeSortOrder = null;
			return;
		}

		Comparator<T> sorter = translateOrderKey(key);

		// if there is no matching sort order, just clear it and leave the order
		// as is.
		if (sorter == null) {
			activeSortOrder = null;
			return;
		}

		// return if we are already sorted by this value then do nothing
		if (sorter.equals(activeSortOrder)) {
			return;
		}
		// when we changed the order, it defaults to ascending
		// orderedAscendingFlag = true;
		activeSortOrder = sorter;
		// sort();
	}

	protected Comparator<T> translateOrderKey(String key) {
		return getOrderKeyMap().get(key);
	}

	public void sort() {
		if (isSorted()) {
			log.debug("Sorting list using {}  ", activeSortOrder);

			Collections.sort(getBackingData(), activeSortOrder);
			if (!orderedAscendingFlag) {
				Collections.reverse(getBackingData());
			}
		}
	}

}
