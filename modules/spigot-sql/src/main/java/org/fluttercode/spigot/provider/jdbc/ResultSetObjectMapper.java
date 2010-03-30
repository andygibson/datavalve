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

package org.fluttercode.spigot.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manufactures objects of type T from the current row of the {@link ResultSet}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface ResultSetObjectMapper<T> {

	/**
	 * Instantiates and object of type T, loads it with values from the current
	 * row of the result set and returns it.
	 * <p/>
	 * Do not modify the result set or change the position. 
	 * 
	 * @param resultSet
	 *            current row contains the data from this instance.
	 * @return a new instance of type T initialized with data from the current
	 *         row of the result set.
	 * @throws SQLException
	 */
	T createObjectFromResultSet(ResultSet resultSet) throws SQLException;

}
