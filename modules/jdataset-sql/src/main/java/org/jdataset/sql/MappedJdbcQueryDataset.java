package org.jdataset.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MappedJdbcQueryDataset extends AbstractJdbcQueryDataset<TableRow>{

	private static final long serialVersionUID = 1L;
	
	public MappedJdbcQueryDataset() {
		super();
	}

	public MappedJdbcQueryDataset(Connection connection) {
		super(connection);
	}

	@Override
	public TableRow createObjectFromResultSet(ResultSet resultSet)
			throws SQLException {
		TableRow result = new TableRow();
		ResultSetMetaData metadata = resultSet.getMetaData();
		
		for (int i = 0;i < metadata.getColumnCount();i++) {
			result.add(metadata.getColumnName(i+1), resultSet.getObject(i+1));
		}
		
		return result;
	}

}
