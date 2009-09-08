package org.jdataset.wicket;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.jdataset.ObjectDataset;

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
