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

package org.fluttercode.spigot.sample.wicket.dataprovider;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.sample.wicket.DatasetInfoPanel;
import org.fluttercode.spigot.sample.wicket.HeaderLinkPanel;
import org.fluttercode.spigot.sample.wicket.WicketApplication;
import org.fluttercode.spigot.wicket.DatasetDataProvider;
import org.phonelist.model.Person;

/**
 * Homepage
 */
/**
 * @author Andy Gibson
 * 
 */
public abstract class AbstractDataProviderPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private ObjectDataset<Person> dataset;

	public WicketApplication getWicketApp() {
		return (WicketApplication) super.getApplication();
	}

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public AbstractDataProviderPage(final PageParameters parameters) {

		add(new HeaderLinkPanel("linkPanel"));
		

		dataset = createDataset();
		dataset.setMaxRows(10);

		ISortableDataProvider<Person> provider = new DatasetDataProvider<Person>(dataset);
		
		// create model for form labels		
		add(new DatasetInfoPanel("infoPanel", dataset));

		// add the grid
		IColumn[] columns = new IColumn[3];
		columns[0] = new PropertyColumn<String>(new Model<String>("Id"), "id", "id");
		columns[1] = new PropertyColumn<String>(new Model<String>("Name"), "name",
				"displayName");
		columns[2] = new PropertyColumn<String>(new Model<String>("Phone"), "phone",
		"phone");
		
		

		DefaultDataTable table = new DefaultDataTable("dataTable", columns, provider,
				10);
		add(table);
	}

	public abstract ObjectDataset<Person> createDataset();
}
