package org.jdataset;

public class SimplePaginator implements Paginator {

	private int firstResult = 0;
	private int maxRows = 0;
	private boolean orderAscending = true;
	private String orderKey;
	private boolean nextAvailable;

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
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

	}

	public void copyPaginationInfo(Paginator target) {
		doCopyPaginationInfo(this,target);
	}

	public static void doCopyPaginationInfo(Paginator source,Paginator target) {
		if (target != null) {
			target.setFirstResult(source.getFirstResult());
			target.setMaxRows(source.getMaxRows());
			target.setOrderKey(source.getOrderKey());
			target.setOrderAscending(source.isOrderAscending());
			target.setNextAvailable(source.isNextAvailable());
		}
	}

	public boolean includeAllResults() {
		return getMaxRows() == 0;
	}

	public boolean isNextAvailable() {
		return nextAvailable;
	}

	public void setNextAvailable(boolean nextAvailable) {
		this.nextAvailable = nextAvailable;
	}
}
