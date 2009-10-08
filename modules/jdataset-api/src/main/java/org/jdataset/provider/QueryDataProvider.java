package org.jdataset.provider;

import java.util.List;
import java.util.Map;

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
/**
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface QueryDataProvider<T> extends StatementDataProvider<T> {

	public Map<String, String> getOrderKeyMap();

	public void setOrderKeyMap(Map<String, String> orderKeyMap);

	public void addRestriction(String restriction);

	/**
	 * Adds a the restriction if <code>value</code> is not null. In the syntax
	 * the phrase <code>:param</code> is used to indicate where the parameter
	 * for the passed in value should go.
	 * 
	 * @param restriction
	 * @param value
	 * @return
	 */
	boolean addRestriction(String restriction, Object value);

	/**
	 * Adds the restriction substituting <code>paramValue</code> for the
	 * <code>:param</code> constant in the restriction if <code>testValue</code>
	 * is not null or it's length is not zero. This works the same as the other
	 * <code>addRestriction</code> methods but is specific to strings and checks
	 * for zero length (but non-null) strings.
	 * 
	 * @param restriction
	 * @param testValue
	 * @param paramValue
	 * @return true if the restriction was added
	 */

	boolean addRestriction(String restriction, String testValue,
			String paramValue);

	/**
	 * Adds the restriction substituting <code>paramValue</code> for the
	 * <code>:param</code> constant in the restriction if <code>testValue</code>
	 * is not null.
	 * 
	 * @param restriction
	 * @param testValue
	 * @param paramValue
	 * @return true if the restriction was added
	 */
	boolean addRestriction(String restriction, Object testValue,
			Object paramValue);

	/**
	 * @return the list of restrictions
	 */
	public List<String> getRestrictions();

	/**
	 * Sets the list of restrictions.
	 * 
	 * @param restrictions
	 *            new list of values
	 */
	public void setRestrictions(List<String> restrictions);

}
