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

package org.fluttercode.spigot;

/**
 * Interface for holding the stateful pagination information such as page size,
 * first page and ordering information. Passed to {@link DataProvider}
 * implementations when requesting data to provide information regarding which
 * information to return, how much to return and the order to return it in.
 * <p/>
 * {@link DataProvider} implementations are responsible for setting the
 * <code>nextAvailable</code> attribute when data is fetched.
 * 
 * 
 * @author Andy Gibson
 * 
 */
public interface Paginator {

	/**
	 * @return The index of the first result to return
	 */
	int getFirstResult();

	/**
	 * @param firstResult
	 *            the index to use for the first result
	 */
	void setFirstResult(int firstResult);

	/**
	 * @return the maximum number of rows to return. Null indicates we are
	 *         returning all rows from firstResult to the end of the dataset
	 */
	Integer getMaxRows();

	/**
	 * @param maxRows
	 *            the maximum number of rows to return
	 */
	void setMaxRows(Integer maxRows);

	/**
	 * @return The key value we are ordering by
	 */
	String getOrderKey();

	/**
	 * Sets the key value used to order the results. The key value can
	 * optionally be used to sort the dataset when the results are fetched. If
	 * the value is set to a different value, isOrderAscending is reset to true.
	 * If the value is set to the same value then the ordering is toggled.
	 * 
	 * @param orderKey
	 *            The key value to order by
	 */
	void setOrderKey(String orderKey);

	/**
	 * @return Whether the order is ascending
	 */
	boolean isOrderAscending();

	/**
	 * @param isAscending
	 *            Set the order for the ordering
	 */
	void setOrderAscending(boolean isAscending);

	/**
	 * This method is used to set the order key, but does so by checking the
	 * current value first. If the new value is the same as the old value, then
	 * the ascending flag is toggled. Otherwise the orderKey is set to the new
	 * value and the ascending flag is set to true.
	 * 
	 * @param orderKey
	 *            The new key value to order by
	 */
	void changeOrderKey(String orderKey);

	void copyPaginationInfo(Paginator target);

	boolean includeAllResults();

	boolean isNextAvailable();

	boolean isPreviousAvailable();

	void setNextAvailable(boolean nextAvailable);

	void next();

	void previous();
}
