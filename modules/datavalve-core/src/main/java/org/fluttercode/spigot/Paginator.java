/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
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
	public int getFirstResult();

	/**
	 * @param firstResult
	 *            the index to use for the first result
	 */
	public void setFirstResult(int firstResult);

	/**
	 * @return the maximum number of rows to return. Null indicates we are
	 *         returning all rows from firstResult to the end of the dataset
	 */
	public Integer getMaxRows();

	/**
	 * @param maxRows
	 *            the maximum number of rows to return
	 */
	public void setMaxRows(Integer maxRows);

	/**
	 * @return The key value we are ordering by
	 */
	public String getOrderKey();

	/**
	 * Sets the key value used to order the results. The key value can
	 * optionally be used to sort the dataset when the results are fetched. If
	 * the value is set to a different value, isOrderAscending is reset to true.
	 * If the value is set to the same value then the ordering is toggled.
	 * 
	 * @param orderKey
	 *            The key value to order by
	 */
	public void setOrderKey(String orderKey);

	/**
	 * @return Whether the order is ascending
	 */
	public boolean isOrderAscending();

	/**
	 * @param isAscending
	 *            Set the order for the ordering
	 */
	public void setOrderAscending(boolean isAscending);

	/**
	 * This method is used to set the order key, but does so by checking the
	 * current value first. If the new value is the same as the old value, then
	 * the ascending flag is toggled. Otherwise the orderKey is set to the new
	 * value and the ascending flag is set to true.
	 * 
	 * @param orderKey
	 *            The new key value to order by
	 */
	public void changeOrderKey(String orderKey);

	public boolean includeAllResults();

	public boolean isNextAvailable();

	public boolean isPreviousAvailable();

	public void setNextAvailable(boolean nextAvailable);

	public void next();

	public void previous();
}
