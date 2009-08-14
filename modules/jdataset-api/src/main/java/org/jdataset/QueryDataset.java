package org.jdataset;

import java.util.List;
import java.util.Map;

import org.jdataset.ParameterizedDataset;

/**
 * The <code>QueryDataset</code> interface extends the {@link ParameterizedDataset}
 * interface and adds methods for handling select and count text statements that
 * may be used for SQL or EJBQL, as well as textual restrictions and mapping
 * order key values to field values.
 * <p>
 * This interface can be used to interact with a dataset that contains some kind
 * of querying for count and selection, where the order key maps to a different
 * representation, and where there are zero or more text defined restrictions.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Object type this dataset contains
 */
public interface QueryDataset<T> extends ParameterizedDataset<T> {

	public abstract String getSelectStatement();

	public abstract void setSelectStatement(String selectStatement);

	public abstract String getCountStatement();

	public abstract void setCountStatement(String countStatement);

	public abstract Map<String, String> getOrderKeyMap();

	public abstract void setOrderKeyMap(Map<String, String> orderKeyMap);
	
	public abstract void addRestriction(String restriction);

	public abstract List<String> getRestrictions();

	public abstract void setRestrictions(List<String> restrictions);

}
