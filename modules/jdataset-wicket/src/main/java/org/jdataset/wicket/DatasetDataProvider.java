package org.jdataset.wicket;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.jdataset.ObjectDataset;

public class DatasetDataProvider<T> extends SortableDataProvider<T> {

	private static final long serialVersionUID = 1L;
	
	private final ObjectDataset<T> dataset;
	
	
	public DatasetDataProvider(ObjectDataset<T> dataset) {
		super();
		this.dataset = dataset;		
	}

	public void detach() {
		dataset.invalidateResultInfo();
	}

	public int size() {
		return dataset.getResultCount();
	}

	public IModel<T> model(Object object) {
		return new CompoundPropertyModel<T>(object);
	}

	public Iterator<T> iterator(int first, int count) {
		dataset.setFirstResult(first);
		dataset.setMaxRows(count);
		if (getSort() != null) {
			dataset.setOrderKey(getSort().getProperty());
			dataset.setOrderAscending(getSort().isAscending());
		} else {
			dataset.setOrderKey(null);
		}
		return dataset.getResultList().iterator();
	}
}
