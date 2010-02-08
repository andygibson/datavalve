package org.jdataset.sample.wicket;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.jdataset.dataset.ObjectDataset;

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
