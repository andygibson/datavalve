/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.AbstractQueryDataProvider;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.provider.util.DataQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the base implementation for a SQL based {@link QueryDataProvider}
 * which uses restrictions, parameters and ordering to define the final results.
 * <p/>
 * Because this is a SQL based dataset, we need some mechanism to turn the raw
 * data into an object. We do this using the template pattern by defining the
 * method
 * {@link AbstractJdbcDataProvider#createListFromResultSet(ResultSet, Integer, Integer)
 * as abstract. This will be implemented in sub classes to convert the result
 * set data into a strongly typed object.
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object that will be returned in the dataset.
 */
public abstract class AbstractJdbcDataProvider<T> extends
		AbstractQueryDataProvider<T> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory
			.getLogger(AbstractJdbcDataProvider.class);

	private transient Connection connection;

	public AbstractJdbcDataProvider() {
		this(null);
	}

	public AbstractJdbcDataProvider(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Creates a new {@link PreparedStatement} for use in fetching the count or
	 * the results.
	 * 
	 * @param query
	 *            {@link DataQuery} instance that contains the query sql and
	 *            parameters
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
			log.debug("Setting parameter {} to '{}'", i, param.getValue());
			statement.setObject(i + 1, param.getValue());
		}

		return statement;

	}

	@Override
	protected Integer queryForCount(DataQuery query) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {

			try {
				statement = buildPreparedStatement(query);
				resultSet = statement.executeQuery();

				if (resultSet.next()) {
					int value = resultSet.getInt(1);
					log.debug("Fetch Result Count SQL returned {}", value);
					return value;
				} else {
					return 0;
				}
			} finally {

				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}

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
		ResultSet resultSet = null;
		try {
			try {
				statement = buildPreparedStatement(query);

				resultSet = statement.executeQuery();

				List<T> results = createListFromResultSet(resultSet,
						firstResult, count);
				log.debug("Results processor returned {} results", results
						.size());
				return results;
			} finally {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return Collections.emptyList();
	}

	/**
	 * Iterate over the given result set calling the
	 * {@link AbstractJdbcQueryDataProvider#resultSetObjectMapper} for each row
	 * in the result set. Each created object is added to the result list which
	 * is then returned to the user.
	 * 
	 * @param resultSet
	 *            list of records we are converting to objects.
	 * @param firstRow
	 *            number of the row we start processing from
	 * @param maxRows
	 *            used to limit the number of rows we process (null indicates
	 *            all rows)
	 * @return List of objects derived from the result set from firstRow to
	 *         firstRow + maxRows or to the end of the resultset if maxRows == 0
	 * @throws SQLException
	 */
	protected List<T> createListFromResultSet(ResultSet resultSet,
			Integer firstRow, Integer maxRows) throws SQLException {
		List<T> results = new ArrayList<T>();

		if (!skipRows(resultSet, firstRow)) {
			return results;
		}

		while (true) {
			results.add(createObjectFromResultSet(resultSet));

			// if this is paged, limit the number of rows loaded
			if (maxRows != null && results.size() == maxRows) {
				return results;
			}

			// if there are no more results, return the list.
			if (!resultSet.next()) {
				return results;
			}
		}
	}

	/**
	 * Moves the result set to the row indicated by first row.
	 * 
	 * @param resultSet
	 *            Result set to skip rows in
	 * @param firstRow
	 *            number of rows we need to skip, null or zero for the first row
	 * @return returns true if there are more rows left to process, or false if
	 *         the end of the result set was reached
	 * @throws SQLException
	 */
	private final boolean skipRows(ResultSet resultSet, Integer firstRow)
			throws SQLException {
		// if firstRow is null, then just return
		if (firstRow == null) {
			firstRow = 0;
		}
		int index = 0;
		while (index < firstRow && resultSet.next()) {
			index++;
		}
		return resultSet.next();
	}

	public abstract T createObjectFromResultSet(ResultSet resultSet)
			throws SQLException;

}
