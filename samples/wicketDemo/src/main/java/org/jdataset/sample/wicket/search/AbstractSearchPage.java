package org.jdataset.sample.wicket.search;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jdataset.ObjectDataset;
import org.jdataset.ParameterizedDataset;
import org.jdataset.ReflectionParameterResolver;
import org.jdataset.sample.wicket.DatasetInfoPanel;
import org.jdataset.sample.wicket.HeaderLinkPanel;
import org.jdataset.sample.wicket.WicketApplication;
import org.jdataset.wicket.DatasetDataProvider;
import org.phonelist.model.Person;

/**
 * Homepage
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
		

		//set up the dataset
		dataset = createDataset();		
		dataset.setMaxRows(10);
		dataset.addParameterResolver(new ReflectionParameterResolver(criteria));
		
		// create model for form labels		
		add(new DatasetInfoPanel("infoPanel", dataset));

		

		//add the search criteria controls bound to the search criteria bean
		IModel<PersonSearchCriteria> criteriaModel = new CompoundPropertyModel<PersonSearchCriteria>(criteria);
		Form<PersonSearchCriteria> form = new Form<PersonSearchCriteria>("criteria",criteriaModel);
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
		
		
		
		//bind the data table to the dataset results
		ISortableDataProvider provider = new DatasetDataProvider<Person>(dataset);		

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

	public abstract ParameterizedDataset<Person> createDataset();
}
