package org.jdataset.provider;

import java.util.List;
import java.util.Map;


/**
 * The <code>QueryDataset</code> interface extends the {@link IStatementDataProvider}
 * interface and adds methods for handling restrictions and mapping order key
 * values to field values.
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
public interface IQueryDataProvider<T> extends IStatementDataProvider<T> {

	public Map<String, String> getOrderKeyMap();

	public void setOrderKeyMap(Map<String, String> orderKeyMap);

	public void addRestriction(String restriction);

	public List<String> getRestrictions();

	public void setRestrictions(List<String> restrictions);

}
