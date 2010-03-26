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

package org.fluttercode.spigot.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andy Gibson
 * 
 */
public class JdbcDataProvider<T> extends AbstractJdbcDataProvider<T> {

	private static final long serialVersionUID = 1L;
	
	private ResultSetObjectMapper<T> resultSetObjectMapper;

	@Override
	public T createObjectFromResultSet(ResultSet resultSet) throws SQLException {
		if (resultSetObjectMapper == null) {
			throw new IllegalStateException("Result set object mapper is null");
		}		
		return resultSetObjectMapper.createObjectFromResultSet(resultSet);
	}
	
	public ResultSetObjectMapper<T> getResultSetObjectMapper() {
		return resultSetObjectMapper;
	}
	
	public void setResultSetObjectMapper(
			ResultSetObjectMapper<T> resultSetObjectMapper) {		
		this.resultSetObjectMapper = resultSetObjectMapper;
	}

}
