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

	public String getSelectStatement();

	public void setSelectStatement(String selectStatement);

	public String getCountStatement();

	public void setCountStatement(String countStatement);

	public Map<String, String> getOrderKeyMap();

	public void setOrderKeyMap(Map<String, String> orderKeyMap);
	
	public void addRestriction(String restriction);

	public List<String> getRestrictions();

	public void setRestrictions(List<String> restrictions);

}
