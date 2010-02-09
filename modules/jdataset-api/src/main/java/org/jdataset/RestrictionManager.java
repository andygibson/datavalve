package org.jdataset;

import java.util.List;

public interface RestrictionManager {
	
	
	/**
	 * Adds a new restriction onto the restriction manager.
	 * 
	 * @param restriction The restriction to add
	 */
	public void add(String restriction);

	/**
	 * Adds a the restriction if <code>value</code> is not null. In the syntax
	 * the phrase <code>:param</code> is used to indicate where the parameter
	 * for the passed in value should go.
	 * <p>
	 * i.e.
	 * 
	 * <pre>
	 * dp.addRestriction(&quot;c.type = :param&quot;, selectedType);
	 * </pre>
	 * 
	 * This will only be added if <code>selectedType</code> is not null.
	 * 
	 * @param restriction restriction to add
	 * @param value value to set the parameter to 
	 * @return true if the restriction was added
	 */
	boolean addRestriction(String restriction, Object value);

	/**
	 * Adds the restriction substituting <code>paramValue</code> for the
	 * <code>:param</code> constant in the restriction if <code>testValue</code>
	 * is not null or it's length is not zero. This works the same as the other
	 * <code>addRestriction</code> methods but is specific to strings and checks
	 * for zero length (but non-null) strings.
	 * <p>
	 * i.e.
	 * 
	 * <pre>
	 * dp.addRestriction(&quot;p.firstName like :param&quot;, firstName, firstName + &quot;%&quot;);
	 * </pre>
	 * 
	 * If the value of <code>firstName</code> is null then the restriction is
	 * not added. If not null, then the restriction is added, but the value is
	 * set to the value of <code>firstName</code> with a wildcard appended. This
	 * makes it easier to include restrictions based on values other than
	 * the value that is checked for null values.
	 * 
	 * @param restriction restriction to add
	 * @param testValue value to check for null. If null, the restriction is not added
	 * @param paramValue value to set the parameter to if <code>testValue</code> is not null.
	 * @return true if the restriction was added
	 */

	boolean addRestriction(String restriction, String testValue,
			String paramValue);

	/**
	 * Adds the restriction substituting <code>paramValue</code> for the
	 * <code>:param</code> constant in the restriction if <code>testValue</code>
	 * is not null.
	 * 
	 * @param restriction restriction to add
	 * @param testValue value to check for null. If null, the restriction is not added
	 * @param paramValue value to set the parameter to if <code>testValue</code> is not null.
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
	
	public ParameterManager getParameterManager();
	public void setParameterManager(ParameterManager parameterManager);
	
	
	
}
