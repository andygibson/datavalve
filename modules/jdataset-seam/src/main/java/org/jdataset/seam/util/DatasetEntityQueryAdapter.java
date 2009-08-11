package org.jdataset.seam.util;

import java.util.List;

import org.jdataset.ObjectDataset;
import org.jdataset.QueryDataset;
import org.jdataset.util.QueryDatasetDecorator;

public class DatasetEntityQueryAdapter<T> extends QueryDatasetDecorator<T> {

	public DatasetEntityQueryAdapter(QueryDataset<T> dataset) {
		super(dataset);
	}

	public DatasetEntityQueryAdapter() {
		// default constructor for Seam
		this(null);
	}

	private ObjectDataset<T> dataset;

	public ObjectDataset<T> getDataset() {
		return dataset;
	}

	public void setDataset(ObjectDataset<T> dataset) {
		this.dataset = dataset;
	}

	public List<T> getResultList() {
		return dataset.getResults();
	}

	public boolean isNextExists() {
		return dataset.isNextAvailable();
	}

	public boolean isPreviousExists() {
		return dataset.isPreviousAvailable();
	}

}
