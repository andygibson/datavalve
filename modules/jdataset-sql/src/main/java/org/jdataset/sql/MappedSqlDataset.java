package org.jdataset.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MappedSqlDataset extends AbstractSqlDataset<TableRow> {

	private static final long serialVersionUID = 1L;
	
	public MappedSqlDataset() {
		super();	
	}

	public MappedSqlDataset(Connection connection) {
		super(connection);
	}

	@Override
	public TableRow createObjectFromResultSet(ResultSet resultSet)
			throws SQLException {
		return TableRow.createTableRowFromResultSet(resultSet);
	}

}
