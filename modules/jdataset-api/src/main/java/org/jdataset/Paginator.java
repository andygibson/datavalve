package org.jdataset;

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
	 * @return the maximum number of rows to return
	 */
	int getMaxRows();

	/**
	 * @param maxRows
	 *            the maximum number of rows to return
	 */
	void setMaxRows(int maxRows);

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
	
	void setNextAvailable(boolean nextAvailable);
}