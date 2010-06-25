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

package org.fluttercode.datavalve.client.wicket;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.fluttercode.datavalve.dataset.ObjectDataset;

/**
 * This implements the {@link ISortableDataProvider} interface to provide a
 * paginated, sorted result set that can be used in a grid in wicket.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains
 */
public class DatasetDataProvider<T> extends SortableDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private final ObjectDataset<T> dataset;

	public DatasetDataProvider(ObjectDataset<T> dataset) {
		super();
		this.dataset = dataset;
	}

	public void detach() {
		dataset.invalidateResultInfo();
	}

	public int size() {
		return dataset.getResultCount();
	}

	public IModel<T> model(Object object) {
		return new CompoundPropertyModel<T>(object);
	}

	public Iterator<T> iterator(int first, int count) {
		dataset.setFirstResult(first);
		dataset.setMaxRows(count);
		if (getSort() != null) {
			dataset.setOrderKey(getSort().getProperty());
			dataset.setOrderAscending(getSort().isAscending());
		} else {
			dataset.setOrderKey(null);
		}
		return dataset.getResultList().iterator();
	}
}
