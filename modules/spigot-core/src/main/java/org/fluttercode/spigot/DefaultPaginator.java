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
