package org.jdataset.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This interface is used to define an object factory that will take a
 * <code>java.sql.ResultSet</code> and return an object representing that row.
 * It is primarily used to generate a list of objects in a <code>java.sql</code>
 * based Dataset.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this factory creates
 */
public interface ResultSetObjectFactory<T> {

	/**
	 * This method accepts a <code>java.sql.ResultSet</code> and returns an
	 * object representing the current row of result set. The object must be of
	 * a type specified in the generic constructor.
	 * 
	 * @param resultSet
	 *            resultset to obtain the data from for the new object
	 * @return The object representing the current row of the passed in result
	 *         set
	 * @throws SQLException
	 */
	public T createObjectFromResultSet(ResultSet resultSet) throws SQLException;

}
