package org.jdataset.impl.provider.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.Parameter;
import org.jdataset.impl.RestrictionBuilder;
import org.jdataset.impl.RestrictionBuilder.ParameterStyle;
import org.jdataset.impl.provider.AbstractQueryDataProvider;
import org.jdataset.impl.provider.DataQuery;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the base implementation for a SQL based {@link QueryDataProvider}
 * which uses restrictions, parameters and ordering to define the final results.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object that will be returned in the dataset.
 */
public abstract class AbstractJdbcQueryDataProvider<T> extends
		AbstractQueryDataProvider<T> implements ResultSetObjectMapper<T> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory
			.getLogger(AbstractJdbcQueryDataProvider.class);

	private transient Connection connection;
	private ResultSetObjectProcessor<T> resultSetObjectProcessor = new ResultSetObjectProcessor<T>();

	public AbstractJdbcQueryDataProvider() {
		this(null);
	}

	public AbstractJdbcQueryDataProvider(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Creates a new {@link PreparedStatement} for use in fetching the count or
	 * the results.
	 * 
	 * @param query {@link DataQuery} instance that contains the query sql and parameters
	 * @return the JDBC prepared statement reflecting the {@link DataQuery}
	 * @throws SQLException
	 */
	private PreparedStatement buildPreparedStatement(DataQuery query)
			throws SQLException {

		PreparedStatement statement = connection.prepareStatement(query
				.getStatement());

		List<Parameter> params = query.getParameters();
		for (int i = 0; i < params.size(); i++) {
			Parameter param = params.get(i);
			log.debug("Setting parameter {} to '{}'", i, param
					.getValue());
			statement.setObject(i + 1, param.getValue());
		}

		return statement;

	}

	@Override
	protected Integer queryForCount(DataQuery query) {
		PreparedStatement statement = null;
		try {
			statement = buildPreparedStatement(query);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int value = rs.getInt(1);
				log.debug("Fetch Result Count SQL returned {}", value);
				return value;
			} else {
				return 0;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	protected List<T> queryForResults(DataQuery query, Integer firstResult,
			Integer count) {
		PreparedStatement statement = null;
		try {
			statement = buildPreparedStatement(query);

			ResultSet resultSet = statement.executeQuery();

			List<T> results = resultSetObjectProcessor.createListFromResultSet(
					resultSet, this, firstResult, count);
			log.debug("Results processor returned {} results", results.size());
			return results;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return Collections.emptyList();
	}

	public abstract T createObjectFromResultSet(ResultSet resultSet)
			throws SQLException;
}
