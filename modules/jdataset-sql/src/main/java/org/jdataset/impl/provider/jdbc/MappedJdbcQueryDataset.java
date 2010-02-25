package org.jdataset.impl.provider.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdataset.dataset.QueryDataset;

/**
 * Implementation of an {@link AbstractJdbcQueryDataProvider} that returns the
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
