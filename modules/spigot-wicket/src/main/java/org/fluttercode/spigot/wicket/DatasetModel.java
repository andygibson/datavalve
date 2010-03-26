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

package org.fluttercode.spigot.wicket;

import org.apache.wicket.model.IModel;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.dataset.ObjectDatasetDecorator;

/**
 * Extends the {@link ObjectDatasetDecorator} class and implements the
 * {@link IModel} interface from wicket and the {@link ObjectDataset} interface.
 * This means that you can have a dataset that decorates any other kind of
 * dataset and can be used directly as a detachable wicket model.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains
 */
public class DatasetModel<T> extends ObjectDatasetDecorator<T> implements
		ObjectDataset<T>, IModel<ObjectDataset<T>> {

	private static final long serialVersionUID = 1L;

	public DatasetModel(ObjectDataset<T> dataset) {
		super(dataset);
	}

	public void detach() {
		getDataset().invalidateResults();
	}

	public ObjectDataset<T> getObject() {
		return getDataset();
	}

	public void setObject(ObjectDataset<T> dataset) {
		setDataset(dataset);
	}

}
