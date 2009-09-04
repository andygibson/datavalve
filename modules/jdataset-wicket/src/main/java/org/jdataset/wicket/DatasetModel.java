package org.jdataset.wicket;

import org.apache.wicket.model.IModel;
import org.jdataset.IObjectDataset;
import org.jdataset.util.ObjectDatasetDecorator;

/**
 * Extends the {@link ObjectDatasetDecorator} class and implements the
 * {@link IModel} interface from wicket and the {@link IObjectDataset} interface.
 * This means that you can have a dataset that decorates any other kind of
 * dataset and can be used directly as a detachable wicket model.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains
 */
public class DatasetModel<T> extends ObjectDatasetDecorator<T> implements
		IObjectDataset<T>, IModel<IObjectDataset<T>> {

	private static final long serialVersionUID = 1L;

	public DatasetModel(IObjectDataset<T> dataset) {
		super(dataset);
	}

	public void detach() {
		getDataset().invalidateResults();
	}

	public IObjectDataset<T> getObject() {
		return getDataset();
	}

	public void setObject(IObjectDataset<T> dataset) {
		setDataset(dataset);
	}

}
