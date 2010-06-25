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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of an {@link AbstractJdbcDataProvider} that returns the
 * results as a set of {@link TableRow} objects that contain the columns as map
 * values. 
 * 
 * <pre>
 * MappedJdbcQueryDataset ds  new MappedJdbcQueryDataset(connection);
 * ds.setSelect("select * from Person p");
 * ds.setCount("select count(1) from Person p");
 * ds.addRestriction("p.id = :id");
 * ds.addRestriction("p.firstName like :firstName");
 * ds.addParameter("id",getId());
 * ds.addParameter("firstName",firstName+"%");
 * 
 * int count = ds.getResultCount();
 * List<TableRow> results = ds.getResultList();
 * log.debug("first Result id = "+results.get(0).getValue("id"));
 * 
 * </pre>
 * 
 * 
 * @author Andy Gibson
 * 
 */
public class MappedJdbcQueryDataset extends AbstractJdbcDataProvider<TableRow> {

	private static final long serialVersionUID = 1L;

	public MappedJdbcQueryDataset() {
		super(null);
	}

	public MappedJdbcQueryDataset(Connection connection) {
		super(connection);
	}
	
	@Override
	public TableRow createObjectFromResultSet(ResultSet resultSet)
			throws SQLException {
		return TableRow.createTableRowFromResultSet(resultSet);
	}

}
