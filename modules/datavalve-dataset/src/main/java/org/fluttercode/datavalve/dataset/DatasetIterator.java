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

package org.fluttercode.datavalve.dataset;

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
