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

package org.fluttercode.spigot.samples.wicketdemo;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.fluttercode.spigot.dataset.ObjectDataset;

/**
 * @author Andy Gibson
 * 
 */
public class DatasetInfoPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DatasetInfoPanel(String id, ObjectDataset<?> dataset) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<ObjectDataset<?>>(dataset));
		add(new Label("firstResult"));
		add(new Label("resultCount"));
		add(new Label("orderKey"));
		add(new Label("orderAscending"));
		add(new Label("maxRows"));
		add(new Label("nextAvailable"));
		add(new Label("previousAvailable"));
		add(new Label("class.name"));
	}

}