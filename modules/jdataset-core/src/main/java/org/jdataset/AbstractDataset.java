package org.jdataset;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;

import org.jdataset.util.DatasetIterator;

/**
 * This is an abstract dataset that provides common implementation for most of
 * the {@link ObjectDataset} methods. It uses a strategy pattern for
 * implementing the fetching of information as using mechanisms defined by
 * subclass implementations.
 * <p>
 * The <code>fetchResultCount()</code> and <code>fetchResults()</code> methods
 * should be overridden in subclasses to implement the fetching of data and the
 * number of records in that data. Apart from these two methods, this class will
 * handle all pagination, and the loading of data as needed by calling the two
 * methods.
 * <p>
 * The record count mechanism is implemented such that it is lazy loaded, and
 * not called until absolutely needed such as the user calls
 * <code>getRecordCount()</code>, <code>getPageCount()</code> or the
 * <code>last()</code> method. This is so we do not have to run this expensive
 * query unless we absolutely have to.
 * <p>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains.
 */
public abstract class AbstractDataset<T> implements ObjectDataset<T>,
		Serializable, Iterable<T> {

	private static final long serialVersionUID = 1L;

	private int firstResult;
	private int maxRows;
	private Integer resultCount;
	private List<T> results;
	private String orderKey;
	private boolean orderAscending = true;
	private Class<?> entityClass;

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public Integer getResultCount() {
		if (resultCount == null) {
			resultCount = fetchResultCount();
		}
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	protected abstract Integer fetchResultCount();

	protected abstract List<T> fetchResults();

	public List<T> getResults() {
		if (results == null) {
			results = fetchResults();
		}
		return results;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		invalidateResults();

	}

	public void setMaxRows(Integer maxRows) {
		if (maxRows < 0) {
			throw new IllegalArgumentException(
					"Max rows returned from query cannot be negative");
		}
		this.maxRows = maxRows;
		invalidateResults();
	}

	public void invalidateResultInfo() {

		resultCount = null;
		results = null;

	}

	public void invalidateResults() {
		results = null;
	}

	public void first() {
		firstResult = 0;
		invalidateResults();
	}

	public boolean isPreviousAvailable() {
		return getFirstResult() > 0;
	}

	public void previous() {
		if (isPreviousAvailable() && allowPaging()) {
			firstResult = firstResult - getMaxRows();
			if (firstResult < 0) {
				firstResult = 0;
			}
			invalidateResults();
		}
	}

	public boolean allowPaging() {
		return !includeAllResults();
	}

	public boolean includeAllResults() {
		return getMaxRows() == 0;
	}

	public void next() {
		if (isNextAvailable() && allowPaging()) {
			firstResult = firstResult + getMaxRows();
			invalidateResults();
		}
	}

	public int getPage() {
		if (!allowPaging()) {
			return 1;
		}

		return (firstResult / getMaxRows()) + 1;
	}

	public void last() {
		// Check if we are returning all rows
		if (includeAllResults()) {
			setFirstResult(0);
			return;
		}

		setFirstResult((getPageCount() - 1) * getMaxRows());
	}

	public int getPageCount() {
		if (includeAllResults()) {
			return 1;
		}
		float result = (float) getResultCount() / getMaxRows();
		if ((int) result != result) {
			result = result + 1;
		}
		return (int) result;

	}

	public boolean isMultiPage() {
		return isNextAvailable() || isPreviousAvailable();
	}

	public Iterator<T> iterator() {
		return new DatasetIterator<T>(this);
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	/**
	 * This method sets the orderKey value, but also compares the existing value
	 * and if they are the same, then the ascending flag is toggled.
	 * 
	 * @param orderKey
	 *            new key value to order by
	 */
	public void changeOrderKey(String orderKey) {
		// if not setting it to null check whether the current value matches the
		// new value and set order ascending flag accordingly
		if (orderKey != null) {
			if (orderKey.equals(this.orderKey)) {
				setOrderAscending(!isOrderAscending());
			} else {
				setOrderKey(orderKey);
				setOrderAscending(true);
			}
		} else {
			// otherwise we are just setting the order to null
			this.orderKey = null;
		}
	}

	public boolean isOrderAscending() {
		return orderAscending;
	}

	public void setOrderAscending(boolean isAscending) {
		this.orderAscending = isAscending;
	}

	public Class<?> getEntityClass() {
		if (entityClass == null) {

			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			entityClass = (Class<?>) type.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	public void refresh() {
		invalidateResults();
	}
}
