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
