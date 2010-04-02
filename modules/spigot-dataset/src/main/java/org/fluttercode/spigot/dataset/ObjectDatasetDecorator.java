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
