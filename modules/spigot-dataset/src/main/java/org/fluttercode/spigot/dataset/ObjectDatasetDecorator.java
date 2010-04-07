/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.dataset;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.fluttercode.spigot.Paginator;

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

	public void setNextAvailable(boolean nextAvailable) {
		this.dataset.setNextAvailable(nextAvailable);
	}

	public boolean includeAllResults() {
		return this.dataset.includeAllResults();
	}
}
