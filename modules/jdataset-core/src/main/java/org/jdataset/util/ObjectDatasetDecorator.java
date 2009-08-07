package org.jdataset.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.jdataset.ObjectDataset;

public class ObjectDatasetDecorator<T> implements ObjectDataset<T>,Serializable {

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

	public int getMaxRows() {
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

	public List<T> getResults() {
		return dataset.getResults();
	}

	public void invalidateResultInfo() {
		dataset.invalidateResultInfo();
	}

	public void invalidateResults() {
		dataset.invalidateResults();
	}

	public boolean isMultiPaged() {
		return dataset.isMultiPaged();
	}

	public boolean isNextAvailable() {
		return dataset.isNextAvailable();
	}

	public boolean isPaged() {
		return dataset.isPaged();
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

	public void setFirstResult(Integer firstResult) {
		dataset.setFirstResult(firstResult);
	}

	public void setMaxRows(Integer maxRows) {
		dataset.setMaxRows(maxRows);
	}
	
	public ObjectDataset<T> getDataset() {
		return dataset;
	}
	
	public void setDataset(ObjectDataset<T> dataset) {
		this.dataset = dataset;
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
}
