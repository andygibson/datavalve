package org.jdataset.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MappedJdbcDataset extends AbstractJdbcDataset<TableRow> {

	private static final long serialVersionUID = 1L;
	
	public MappedJdbcDataset() {
		super();	
	}

	public MappedJdbcDataset(Connection connection) {
		super(connection);
	}

	@Override
	public TableRow createObjectFromResultSet(ResultSet resultSet)
			throws SQLException {
		return TableRow.createTableRowFromResultSet(resultSet);
	}

}
