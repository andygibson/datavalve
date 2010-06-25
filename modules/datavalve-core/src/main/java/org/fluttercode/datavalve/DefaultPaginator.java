/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve;

import java.io.Serializable;


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
