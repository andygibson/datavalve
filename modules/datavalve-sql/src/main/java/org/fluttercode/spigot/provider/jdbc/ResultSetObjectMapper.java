/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
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
