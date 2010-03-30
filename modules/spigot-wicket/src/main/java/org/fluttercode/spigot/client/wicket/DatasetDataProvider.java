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

package org.fluttercode.spigot.client.wicket;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.fluttercode.spigot.dataset.ObjectDataset;

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
