package org.jdataset.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdataset.IPaginator;
import org.jdataset.provider.IParameterizedDataProvider;
import org.jdataset.provider.IStatementDataProvider;
import org.jdataset.provider.impl.AbstractParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Base implementation for the {@link IStatementDataProvider} interface which adds select and count
 * SQL statements on to the {@link IParameterizedDataProvider} interface. The class
 * extends the {@link AbstractParameterizedDataProvider} to use the implementation of
 * the {@link IParameterizedDataProvider} interface. This class also implements the
 * actual fetching of the result count and the results from the database once
 * the parameters have been resolved.
 * </p>
 * <p>
 * Once the JDBC result set has been fetched, this class uses the
 * {@link ResultSetObjectProcessor} to convert the {@link ResultSet} to a list
 * of objects.
 * </p>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object this dataset will return
 */
public abstract class AbstractJdbcDataProvider<T> extends
		AbstractParameterizedDataProvider<T> implements IStatementDataProvider<T>,
		ResultSetObjectMapper<T> {

	private static final long serialVersionUID = 1L;
		
	private transient Connection connection;
	private String countStatement;
	private String selectStatement;
	private ResultSetObjectProcessor<T> resultSetObjectProcessor = new ResultSetObjectProcessor<T>();

	private static Logger log = LoggerFactory
			.getLogger(AbstractJdbcDataProvider.class);

	public AbstractJdbcDataProvider() {
		this(null);
	}

	public AbstractJdbcDataProvider(Connection connection) {
		this.connection = connection;
	}

	private final PreparedStatement buildStatement(String sql) {
		PreparedStatement statement = null;
		log.debug("Building Statement with sql = {}", sql);

		try {
			String[] params = getParameterParser().extractParameters(sql);
			List<Object> paramValues = new ArrayList<Object>();

			// first replace the params in the sql expression
			for (String param : params) {
				log.debug("Evaluating param : {}", param);
				sql = sql.replace(param, "?");
				Object value = resolveParameter(param);
				log.debug("Resolved to  : {}", value);
				paramValues.add(resolveParameter(param));
			}
			log.debug("Final sql = {}", sql);
			statement = connection.prepareStatement(sql);
			statement.setFetchDirection(ResultSet.FETCH_FORWARD);

			// assign the parameter values in the order they were in the sql
			for (int i = 0; i < paramValues.size(); i++) {
				statement.setObject(i + 1, paramValues.get(i));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return statement;
	}

	@Override
	public Integer fetchResultCount() {
		int count = 0;
		try {
			PreparedStatement statement = buildStatement(getCountStatement());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}
	
	public List<T> fetchResults(IPaginator paginator) {
		try {
			PreparedStatement statement = buildStatement(getSelectStatement());
			ResultSet resultSet = statement.executeQuery();
			List<T> results = resultSetObjectProcessor.createListFromResultSet(
					resultSet, this, paginator.getFirstResult(), paginator.getMaxRows()); // createListFromResultSet(resultSet);
			paginator.setNextAvailable(resultSet.next());
			
			return results;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return Collections.emptyList();
	}

	public abstract T createObjectFromResultSet(ResultSet resultSet)
			throws SQLException;

	public String getSelectStatement() {
		return selectStatement;
	}

	public void setSelectStatement(String selectStatement) {
		this.selectStatement = selectStatement;
	}

	public String getCountStatement() {
		return countStatement;
	}

	public void setCountStatement(String countStatement) {
		this.countStatement = countStatement;
	}
}
