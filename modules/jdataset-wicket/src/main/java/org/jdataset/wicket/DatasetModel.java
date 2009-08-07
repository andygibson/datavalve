package org.jdataset.wicket;

import org.apache.wicket.model.IModel;
import org.jdataset.ObjectDataset;
import org.jdataset.util.ObjectDatasetDecorator;

public class DatasetModel<T> extends ObjectDatasetDecorator<T> implements ObjectDataset<T>,IModel<ObjectDataset<T>>{

	private static final long serialVersionUID = 1L;

	public DatasetModel(ObjectDataset<T> dataset) {
		super(dataset);
	}

	public void detach() {
		getDataset().invalidateResults();		
	}


	public ObjectDataset<T> getObject() {
		return getDataset();
	}


	public void setObject(ObjectDataset<T> dataset) {
		setDataset(dataset);
	}

}
