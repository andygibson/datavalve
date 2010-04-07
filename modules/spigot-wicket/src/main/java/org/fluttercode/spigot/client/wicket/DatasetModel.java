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

package org.fluttercode.spigot.client.wicket;

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
