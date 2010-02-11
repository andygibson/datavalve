package org.jdataset.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Extends the {@link AbstractJdbcDataProvider} and implements the
 * {@link ResultSetObjectMapper} as a delegate property. This means we can
 * define classes that implement the conversion from {@link ResultSet} to an
 * Object and re-use them. This is particularly useful if we have a dependency
 * injection framework as part of the application since it can be injected as a
 * dependency. This is as opposed to the non-delegate version that needs
 * overriding or an anonymous class to implement the mapping.
 * <p>
 * If no delegate is assigned to the {@link ResultSetObjectMapper} property then
 * a {@link NullPointerException} is thrown.
 * 
 * @see JdbcQueryDatasetDelegate
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset returns
 */
public class JdbcDatasetDelegate<T> extends AbstractJdbcDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private ResultSetObjectMapper<T> resultSetObjectMapper;

	@Override
	public T createObjectFromResultSet(ResultSet resultSet) throws SQLException {
		if (resultSetObjectMapper == null) {
			throw new NullPointerException("Result set object mapper is null");
		}
		return resultSetObjectMapper.createObjectFromResultSet(resultSet);
	}

}
