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

package org.fluttercode.datavalve.samples.wicketdemo.search;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.fluttercode.datavalve.client.wicket.DatasetDataProvider;
import org.fluttercode.datavalve.dataset.ParameterizedDataset;
import org.fluttercode.datavalve.params.ReflectionParameterResolver;
import org.fluttercode.datavalve.samples.wicketdemo.DatasetInfoPanel;
import org.fluttercode.datavalve.samples.wicketdemo.HeaderLinkPanel;
import org.fluttercode.datavalve.samples.wicketdemo.WicketApplication;
import org.fluttercode.datavalve.samples.wicketdemo.model.Person;

/**
 * Homepage
 */
/**
 * @author Andy Gibson
 * 
 */
public abstract class AbstractSearchPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private ParameterizedDataset<Person> dataset;
	private final PersonSearchCriteria criteria = new PersonSearchCriteria();

	public WicketApplication getWicketApp() {
		return (WicketApplication) super.getApplication();
	}

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public AbstractSearchPage(final PageParameters parameters) {

		add(new HeaderLinkPanel("linkPanel"));

		dataset = createDataset();
		dataset.setMaxRows(10);
		dataset.getProvider().addParameterResolver(new ReflectionParameterResolver(criteria));

		// create model for form labels
		add(new DatasetInfoPanel("infoPanel", dataset));

		// add the search criteria controls bound to the search criteria bean
		IModel<PersonSearchCriteria> criteriaModel = new CompoundPropertyModel<PersonSearchCriteria>(
				criteria);
		Form<PersonSearchCriteria> form = new Form<PersonSearchCriteria>(
				"criteria", criteriaModel);
		add(form);
		form.add(new TextField("id"));
		form.add(new TextField("firstName"));
		form.add(new TextField("lastName"));
		form.add(new TextField("phone"));
		form.add(new Button("search") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				dataset.refresh();
			}
		});

		// bind the data table to the dataset results
		ISortableDataProvider provider = new DatasetDataProvider<Person>(
				dataset);

		// add the grid
		IColumn[] columns = new IColumn[3];
		columns[0] = new PropertyColumn<String>(new Model<String>("Id"), "id",
				"id");
		columns[1] = new PropertyColumn<String>(new Model<String>("Name"),
				"name", "displayName");
		columns[2] = new PropertyColumn<String>(new Model<String>("Phone"),
				"phone", "phone");

		DefaultDataTable table = new DefaultDataTable("dataTable", columns,
				provider, 10);
		add(table);
	}

	public abstract ParameterizedDataset<Person> createDataset();

}
