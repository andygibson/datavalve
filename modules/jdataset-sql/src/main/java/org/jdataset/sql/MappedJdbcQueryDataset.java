package org.jdataset.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Implementation of an {@link AbstractJdbcQueryDataProvider} that returns the
 * results as a set of {@link TableRow} objects that contain the columns as map
 * values. Since this is implements the {@link QueryDatase} interface, you can
 * apply parameterized restrictions and ordering to the query. The select/count
 * statement should contain only the select part and the dataset will add the
 * rest.
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
public class MappedJdbcQueryDataset extends AbstractJdbcQueryDataProvider<TableRow> {

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

		for (int i = 0; i < metadata.getColumnCount(); i++) {
			result.add(metadata.getColumnName(i + 1), resultSet
					.getObject(i + 1));
		}

		return result;
	}

}
