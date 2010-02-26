package org.jdataset.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manufactures objects of type T from the current row of the {@link ResultSet}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface ResultSetObjectMapper<T> {

	/**
	 * Instantiates and object of type T, loads it with values from the current
	 * row of the result set and returns it.
	 * <p/>
	 * Do not modify the result set or change the position. 
	 * 
	 * @param resultSet
	 *            current row contains the data from this instance.
	 * @return a new instance of type T initialized with data from the current
	 *         row of the result set.
	 * @throws SQLException
	 */
	T createObjectFromResultSet(ResultSet resultSet) throws SQLException;

}
