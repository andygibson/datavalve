package org.jdataset.impl;

import java.io.Serializable;

import org.jdataset.Paginator;

/**
 * Default and complete implementation of the {@link Paginator} interface.
 *  
 * @author Andy Gibson
 *
 */
public class DefaultPaginator implements Paginator, Serializable {

	private static final long serialVersionUID = 1L;

	private int firstResult = 0;
	private Integer maxRows;
	private boolean orderAscending = true;
	private String orderKey;
	private boolean nextAvailable;

	public DefaultPaginator() {
		this(null);		
	}
	
	public DefaultPaginator(Integer maxRows) {
		this.maxRows = maxRows;
	}

	
	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(Integer maxRows) {
		this.maxRows = maxRows;
	}

	public boolean isOrderAscending() {
		return orderAscending;
	}

	public void setOrderAscending(boolean orderAscending) {
		this.orderAscending = orderAscending;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public void changeOrderKey(String orderKey) {		
		if (this.orderKey == null) {
			this.orderKey = orderKey;
			return;			
		}
		//toggle it
		if (this.orderKey.equals(orderKey)) {
			this.orderAscending = !this.orderAscending;
			return;
		}
		this.orderKey = orderKey;
		this.orderAscending = true;
		

	}

	public void copyPaginationInfo(Paginator target) {
		doCopyPaginationInfo(this, target);
	}

	public static void doCopyPaginationInfo(Paginator source, Paginator target) {
		if (target != null) {
			target.setFirstResult(source.getFirstResult());
			target.setMaxRows(source.getMaxRows());
			target.setOrderKey(source.getOrderKey());
			target.setOrderAscending(source.isOrderAscending());
			target.setNextAvailable(source.isNextAvailable());
		}
	}

	public boolean includeAllResults() {
		return getMaxRows() == null;
	}

	public boolean isNextAvailable() {
		return nextAvailable;
	}

	public void setNextAvailable(boolean nextAvailable) {
		this.nextAvailable = nextAvailable;
	}

	public void next() {
		if (isNextAvailable()) {
			firstResult = firstResult + getMaxRows();
		}
	}

	public void previous() {
		if (isPreviousAvailable()) {
			firstResult -= getMaxRows();
			if (firstResult < 0) {
				firstResult = 0;
			}
		}
	}

	public boolean isPreviousAvailable() {
		return firstResult > 0;
	}
}
