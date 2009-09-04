package org.jdataset.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdataset.provider.IQueryDataProvider;

/**
 * Implementation of an {@link AbstractJdbcDataProvider} that returns the results as
 * a set of {@link TableRow} objects that contain the columns as map values.
 * <p>
 * Since this is not a structured {@link IQueryDataProvider} you must provide the
 * select, where and order by clauses manually in the
 * {@link #setSelectStatement(String)} and {@link #setCountStatement(String)}
 * statements. To use a {@link IQueryDataProvider} based query, you can use the
 * {@link MappedJdbcQueryDataset} dataset instead.
 * 
 * @author Andy Gibson
 * 
 *         <pre>
 * MappedJdbcDataset ds  new MappedJdbcDataset(connection);
 * ds.setSelect(&quot;select * from Person p where p.lastName = :lastNameParam order by firstName&quot;);
 * ds.setCount(&quot;select count(1) from Person p where p.lastName = :lastNameParam&quot;);
 * ds.addParameter(&quot;lastNameParam&quot;,&quot;SMITH&quot;);
 * 
 * int count = ds.getResultCount();
 * List&lt;TableRow&gt; results = ds.getResultList();
 * assertEqual(count,results.size());
 *  
 * //print out each row
 * for (TableRow row : ds) {
 *     log.debug(row.getValue(&quot;id&quot;)+&quot; , &quot; +  row.getValue(&quot;phone&quot;));
 * }
 * 
 * </pre>
 * 
 * @see MappedJdbcQueryDataset
 */
public class MappedJdbcDataProvider extends AbstractJdbcDataProvider<TableRow> {

	private static final long serialVersionUID = 1L;

	public MappedJdbcDataProvider() {
		super();
	}

	public MappedJdbcDataProvider(Connection connection) {
		super(connection);
	}

	@Override
	public TableRow createObjectFromResultSet(ResultSet resultSet)
			throws SQLException {
		return TableRow.createTableRowFromResultSet(resultSet);
	}

}
