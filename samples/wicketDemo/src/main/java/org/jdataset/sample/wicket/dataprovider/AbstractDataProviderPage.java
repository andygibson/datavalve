package org.jdataset.sample.wicket.dataprovider;

import java.util.Iterator;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jdataset.ObjectDataset;
import org.jdataset.sample.wicket.DatasetInfoPanel;
import org.jdataset.sample.wicket.HeaderLinkPanel;
import org.jdataset.sample.wicket.WicketApplication;
import org.jdataset.wicket.DatasetDataProvider;
import org.jdataset.wicket.DatasetModel;
import org.phonelist.model.Person;

/**
 * Homepage
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

		ISortableDataProvider provider = new DatasetDataProvider<Person>(dataset);
		
/*		ISortableDataProvider dp = new SortableDataProvider() {

			private static final long serialVersionUID = 1L;

			public void detach() {
				dataset.invalidateResultInfo();
			}

			public int size() {
				return dataset.getResultCount();
			}

			public IModel model(Object object) {
				return new CompoundPropertyModel(object);
			}

			public Iterator iterator(int first, int count) {
				System.out.println("Setting record scope");
				dataset.setFirstResult(first);
				dataset.setMaxRows(count);				
				if (getSort() != null) {
					System.out.println("setting order to : "+getSort());
					dataset.setOrderKey(getSort().getProperty());
					dataset.setOrderAscending(getSort().isAscending());
				} else {
					dataset.setOrderKey(null);
				}
				return dataset.getResults().iterator();
			}
		};*/

		// create model for form labels
		IModel model = new DatasetModel<Person>(dataset);
		add(new DatasetInfoPanel("infoPanel", dataset));

		// add the grid
		IColumn[] columns = new IColumn[3];
		columns[0] = new PropertyColumn(new Model("Id"), "id", "id");
		columns[1] = new PropertyColumn(new Model("Name"), "name",
				"displayName");
		columns[2] = new PropertyColumn(new Model("Phone"), "phone",
		"phone");
		

		DefaultDataTable table = new DefaultDataTable("dataTable", columns, provider,
				10);
		add(table);
	}

	public abstract ObjectDataset<Person> createDataset();
}
