package org.jdataset.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of an {@link AbstractJdbcDataset} that returns the results as
 * a set of {@link TableRow} objects that contain the columns as map values.
 * 
 * @author Andy Gibson
 * 
 */
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
