package org.jdataset;

import java.io.Serializable;
import java.util.List;

/**
 * This is an abstract dataset that provides common implementation for most of
 * the {@link ObjectDataset} interface methods. It uses a strategy pattern for
 * implementing the fetching of information as defined by subclass
 * implementations.
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
 * <code>last()</code> method.
 * <p>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains.
 */
public abstract class AbstractDataset<T> implements ObjectDataset<T>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private int firstResult;
	private int maxRows;
	private Integer resultCount;
	private List<T> results;

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

	public void setFirstResult(Integer firstResult) {
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

	public boolean isPaged() {
		return getMaxRows() != 0;
	}

	public boolean isMultiPaged() {
		return isNextAvailable() || isPreviousAvailable();
	}
}
