/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.provider;

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
public interface QueryDataProvider<T> extends StatementDataProvider<T> {

	public Map<String, String> getOrderKeyMap();

	public void setOrderKeyMap(Map<String, String> orderKeyMap);

	public List<String> getRestrictions();

	public void setRestrictions(List<String> restrictions);

	/** 
	 * Adds a constant restriction
	 * 
	 * @param restriction the restriction to add
	 */
	public void addRestriction(String restriction);

	/**
	 * Adds a the restriction if <code>value</code> is not null. In the syntax
	 * the phrase <code>:param</code> is used to indicate where the parameter
	 * for the passed in value should go.
	 * <p>
	 * i.e.
	 * <pre>
	 * dp.addRestriction(&quot;c.type = :param&quot;, selectedType);
	 * </pre>
	 * 
	 * This will only be added if <code>selectedType</code> is not null.
	 * 
	 * @param restriction
	 *            restriction to add
	 * @param value
	 *            value to set the parameter to
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
	 * makes it easier to include restrictions based on values other than the
	 * value that is checked for null values.
	 * 
	 * @param restriction
	 *            restriction to add
	 * @param testValue
	 *            value to check for null. If null, the restriction is not added
	 * @param paramValue
	 *            value to set the parameter to if <code>testValue</code> is not
	 *            null.
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
	 *            restriction to add
	 * @param testValue
	 *            value to check for null. If null, the restriction is not added
	 * @param paramValue
	 *            value to set the parameter to if <code>testValue</code> is not
	 *            null.
	 * @return true if the restriction was added
	 */
	boolean addRestriction(String restriction, Object testValue,
			Object paramValue);

}
