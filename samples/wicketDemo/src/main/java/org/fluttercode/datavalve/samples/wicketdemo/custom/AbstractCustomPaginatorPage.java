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

package org.fluttercode.datavalve.samples.wicketdemo.custom;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.fluttercode.datavalve.client.wicket.DatasetModel;
import org.fluttercode.datavalve.dataset.ObjectDataset;
import org.fluttercode.datavalve.samples.wicketdemo.DatasetInfoPanel;
import org.fluttercode.datavalve.samples.wicketdemo.HeaderLinkPanel;
import org.fluttercode.datavalve.samples.wicketdemo.WicketApplication;
import org.fluttercode.datavalve.samples.wicketdemo.model.Person;

/**
 * This is the abstract page for displaying the custom paginator. 
 */
/**
 * @author Andy Gibson
 * 
 */
public abstract class AbstractCustomPaginatorPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private ObjectDataset<Person> dataset;
	private final Button prev;
	private final Button next;
	private final Button first;
	private final Button last;

	public WicketApplication getWicketApp() {
		return (WicketApplication) super.getApplication();
	}

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public AbstractCustomPaginatorPage(final PageParameters parameters) {

		add(new HeaderLinkPanel("linkPanel"));
		// Add the simplest type of label
		dataset = createDataset();
		dataset.setMaxRows(10);

		IModel<ObjectDataset<Person>> model = new DatasetModel<Person>(dataset);
		add(new DatasetInfoPanel("infoPanel", dataset));

		// give the model for the page
		setDefaultModel(new CompoundPropertyModel<ObjectDataset<Person>>(model));

		add(new PropertyListView("results") {

			@Override
			protected void populateItem(ListItem item) {
				item.add(new Label("id"));
				item.add(new Label("displayName"));
				item.add(new Label("phone"));
			}
		});
		Form form = new Form("form");

		prev = new Button("btnPrevious") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				dataset.previous();
				configButtons();
			}

		};
		next = new Button("btnNext") {
			public void onSubmit() {
				super.onSubmit();
				dataset.next();
				configButtons();
			};
		};

		first = new Button("btnFirst") {
			public void onSubmit() {
				super.onSubmit();
				dataset.first();
				configButtons();
			};
		};

		last = new Button("btnLast") {
			public void onSubmit() {
				super.onSubmit();
				dataset.last();
				configButtons();
			};
		};

		configButtons();
		form.add(next);
		form.add(prev);
		form.add(first);
		form.add(last);
		form.add(new Label("page"));
		form.add(new Label("pageCount"));
		add(form);
	}

	public void configButtons() {
		next.setEnabled(dataset.isNextAvailable());
		prev.setEnabled(dataset.isPreviousAvailable());
	}

	public abstract ObjectDataset<Person> createDataset();
}
