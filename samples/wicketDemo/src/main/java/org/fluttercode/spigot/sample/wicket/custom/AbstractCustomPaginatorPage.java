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

package org.fluttercode.spigot.sample.wicket.custom;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.sample.wicket.DatasetInfoPanel;
import org.fluttercode.spigot.sample.wicket.HeaderLinkPanel;
import org.fluttercode.spigot.sample.wicket.WicketApplication;
import org.fluttercode.spigot.wicket.DatasetModel;
import org.phonelist.model.Person;

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
