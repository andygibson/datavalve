package org.jdataset.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Extends the {@link AbstractJdbcQueryDataset} and implements the
 * {@link ResultSetObjectMapper} as a delegate property. This means we can
 * define classes that implement the conversion from {@link ResultSet} to an
 * Object and re-use them. This is particularly useful if we have a dependency
 * injection framework as part of the application since it can be injected as a
 * dependency.
 * <p>
 * If no delegate is assigned to the {@link ResultSetObjectMapper} property
 * then a {@link NullPointerException} is thrown.
 * 
 * @see JdbcDatasetDelegate
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset returns
 */
public class JdbcQueryDatasetDelegate<T> extends AbstractJdbcDataset<T> {

	private ResultSetObjectMapper<T> resultSetObjectMapper;

	@Override
	public T createObjectFromResultSet(ResultSet resultSet) throws SQLException {
		if (resultSetObjectMapper == null) {
			throw new NullPointerException("Result set object mapper is null");
		}
		return resultSetObjectMapper.createObjectFromResultSet(resultSet);
	}

}
