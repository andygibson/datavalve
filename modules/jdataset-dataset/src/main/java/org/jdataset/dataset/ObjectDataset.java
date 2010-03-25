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

package org.jdataset.dataset;

import java.util.List;

import org.jdataset.Paginator;

/**
 * This is the base interface for the stateful object datasets. It provides
 * methods for accessing the data, record counts, pagination and navigation of
 * the dataset. It inherits from {@link Iterable} so we can iterate over
 * instances of the interface. It also inherits from {@link Paginator} so we can
 * use this interface in place of the {@link Paginator}.
 * <p>
 * When writing code to interface with a basic dataset, you should code to this
 * interface as much as possible. This interface specifies how you access the
 * information without any methods as to how to determine what information the
 * dataset should contain.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of value this dataset returns
 */
public interface ObjectDataset<T> extends Iterable<T>, Paginator {

	/**
	 * Returns the list of objects for this dataset
	 * 
	 * @return The list of results for this query
	 */
	List<T> getResultList();

	/**
	 * @return The total number of results for this query
	 */
	Integer getResultCount();

	/**
	 * Causes the list of results to be invalidated and re-fetched on the next
	 * request. This is usually called when the first result or result count
	 * changes
	 */
	void invalidateResults();

	/**
	 * Called when both the result list and the result count are invalid. This
	 * method is called when the data this dataset provides has changed and
	 * therefore the count might have changed.
	 */
	void invalidateResultInfo();

	/**
	 * @return The current page based on the first result and the max rows
	 *         returned
	 */
	int getPage();

	/**
	 * @return The number of pages based on the resultCount and the max rows
	 *         returned
	 */
	int getPageCount();

	/**
	 * Sets the first result index to 0, fetching the first page of results, or
	 * refreshing all results if paging is not used.
	 */
	void first();

	/**
	 * Goes to the last page of results, or refreshes the results from the first
	 * page if paging is not used.
	 */
	void last();

	/**
	 * @return Indicates whether this dataset has more than one page. Can be
	 *         used to indicate whether paging is needed. This value is based on
	 *         the actual results as opposed to the dataset settings.
	 */
	boolean isMultiPage();

	/**
	 * @return Return the class type this dataset returns.
	 */
	Class<?> getEntityClass();

	/**
	 * Called to invalidate the data and the result info so it will be re-read
	 * next time it is requested.
	 */
	void refresh();
}
