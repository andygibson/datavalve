/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider.jdbc;

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
