package org.jdataset.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.jdataset.ObjectDataset;
import org.jdataset.Paginator;

/**
 * Decorator class that can be used to decorate an existing
 * {@link ObjectDataset}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains
 */
public class ObjectDatasetDecorator<T> implements ObjectDataset<T>,
		Serializable {

	private static final long serialVersionUID = 1L;

	private ObjectDataset<T> dataset;

	public ObjectDatasetDecorator(ObjectDataset<T> dataset) {
		this.dataset = dataset;
	}

	public void first() {
		dataset.first();
	}

	public int getFirstResult() {
		return dataset.getFirstResult();
	}

	public Integer getMaxRows() {
		return dataset.getMaxRows();
	}

	public int getPage() {
		return dataset.getPage();
	}

	public int getPageCount() {
		return dataset.getPageCount();
	}

	public Integer getResultCount() {
		return dataset.getResultCount();
	}

	public List<T> getResultList() {
		return dataset.getResultList();
	}

	public void invalidateResultInfo() {
		dataset.invalidateResultInfo();
	}

	public void invalidateResults() {
		dataset.invalidateResults();
	}

	public boolean isMultiPage() {
		return dataset.isMultiPage();
	}

	public boolean isNextAvailable() {
		return dataset.isNextAvailable();
	}

	public boolean isPreviousAvailable() {
		return dataset.isPreviousAvailable();
	}

	public void last() {
		dataset.last();
	}

	public void next() {
		dataset.next();
	}

	public void previous() {
		dataset.previous();
	}

	public void setFirstResult(int firstResult) {
		dataset.setFirstResult(firstResult);
	}

	public void setMaxRows(Integer maxRows) {
		dataset.setMaxRows(maxRows);
	}

	public ObjectDataset<T> getDataset() {
		return dataset;
	}

	public Iterator<T> iterator() {
		return this.dataset.iterator();
	}

	public String getOrderKey() {
		return dataset.getOrderKey();
	}

	public boolean isOrderAscending() {
		return dataset.isOrderAscending();
	}

	public void setOrderAscending(boolean isAscending) {
		dataset.setOrderAscending(isAscending);
	}

	public void setOrderKey(String orderKey) {
		dataset.setOrderKey(orderKey);
	}

	public void changeOrderKey(String orderKey) {
		dataset.changeOrderKey(orderKey);
	}

	public Class<?> getEntityClass() {
		return dataset.getEntityClass();
	}

	public void refresh() {
		dataset.refresh();
	}

	public void setDataset(ObjectDataset<T> dataset) {
		this.dataset = dataset;
	}

	public void copyPaginationInfo(Paginator target) {

		if (target != null) {
			target.setFirstResult(getFirstResult());
			target.setMaxRows(getMaxRows());
			target.setOrderKey(getOrderKey());
			target.setOrderAscending(isOrderAscending());
		}
	}

	public void setNextAvailable(boolean nextAvailable) {
		this.dataset.setNextAvailable(nextAvailable);
	}

	public boolean includeAllResults() {
		return this.dataset.includeAllResults();
	}
}
