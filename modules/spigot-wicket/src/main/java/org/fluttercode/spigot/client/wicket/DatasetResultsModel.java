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
