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

package org.jdataset.dataset;

import java.util.Iterator;

/**
 * Iterator implementation for the iterator that is returned from the
 * {@link ObjectDataset#iterator()} method.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this iterator returns
 */
public class DatasetIterator<T> implements Iterator<T> {

	private static final long serialVersionUID = 1L;
	private final ObjectDataset<T> dataset;
	private int index;

	public DatasetIterator(ObjectDataset<T> dataset) {
		super();
		if (dataset == null) {
			throw new IllegalArgumentException(
					"Cannot pass null to the dataset iterator");
		}
		this.dataset = dataset;
		dataset.first();
		index = 0;

	}

	public boolean hasNext() {
		if (index < dataset.getResultList().size()) {
			return true;
		}

		return dataset.isNextAvailable();
	}

	public T next() {
		if (index == dataset.getResultList().size()) {
			dataset.next();
			index = 0;
		}
		if (index < dataset.getResultList().size()) {
			return dataset.getResultList().get(index++);
		}

		throw new IllegalStateException("Unable to return item");
	}

	public void remove() {
		throw new UnsupportedOperationException(
				"Dataset iterators do not support element removal");

	}

}
