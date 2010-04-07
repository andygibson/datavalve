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

import java.util.List;

import org.apache.wicket.model.IModel;
import org.fluttercode.spigot.dataset.ObjectDataset;

/**
 * @author Andy Gibson
 * 
 */
public class DatasetResultsModel<T> implements IModel<List<T>> {

	private static final long serialVersionUID = 1L;
	
	private ObjectDataset<T> dataset;

	public DatasetResultsModel(ObjectDataset<T> dataset) {
		super();
		this.dataset = dataset;
	}

	public List<T> getObject() {
		return dataset.getResultList();
	}

	public void setObject(List<T> object) {
		//do nothing
	}

	public void detach() {
		dataset.invalidateResults();
	}

}
