package org.jdataset.provider;

import org.jdataset.OrderManager;
import org.jdataset.RestrictionManager;

/**
 * The <code>QueryDataset</code> interface extends the
 * {@link StatementDataProvider} interface and adds methods for handling
 * restrictions and mapping order key values to field values.
 * <p>
 * This interface can be used to implement with a dataset that contains a
 * mechanism for querying count and selection results. For these datasets the
 * order key maps to a different dataset specific representation, and there are
 * zero or more text defined restrictions.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Object type this dataset contains
 */
public interface QueryDataProvider<T> extends StatementDataProvider<T> {

	public OrderManager getOrderHandler();

	public RestrictionManager getRestrictionHandler();

}
