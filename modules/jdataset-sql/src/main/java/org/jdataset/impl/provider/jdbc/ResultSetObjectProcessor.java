package org.jdataset.impl.provider.jdbc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This helper class takes a <code>java.sql.ResultSet</code> and turns it into a
 * list objects. This was separated out because we couldn't depend on an
 * inheritance hierarchy to pass along the functionality so I used composition
 * instead. This class allows us to share the logic for converting Result sets
 * to objects in different <code>java.sql</code> driven datasets.
 * </p>
 * <p>
 * This processor takes a result set, skips the first N rows as defined by the
 * first row parameter and returns X number of rows based on the maximum number
 * of rows to return and the number of rows available. For each row it returns,
 * it calls the <@link {@link ResultSetObjectMapper}> passed in. This method is
 * typically defined in the calling dataset as abstract and needs to be
 * implemented by the developer to handle the particular <code>ResultSet</code>
 * to <code>Object</code> mapping.
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this result set will return
 */
public class ResultSetObjectProcessor<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Iterate over the given result set calling the object mapper for each row
	 * in the result set. Each created object is added to the result list which
	 * is then returned to the user.
	 * 
	 * @param resultSet
	 *            list of records we are converting to objects.
	 * @param objectMapper
	 *            mapper that will create an object based on the current row of
	 *            the result set we pass to it.
	 * @param firstRow
	 *            number of the row we start processing from
	 * @param maxRows
	 *            used to limit the number of rows we process (0 indicates all
	 *            rows)
	 * @return List of objects derived from the result set from firstRow to
	 *         firstRow + maxRows or to the end of the resultset if maxRows == 0
	 * @throws SQLException
	 */
	public List<T> createListFromResultSet(ResultSet resultSet,
			ResultSetObjectMapper<T> objectMapper, int firstRow, int maxRows)
			throws SQLException {
		List<T> results = new ArrayList<T>();

		if (!skipRows(resultSet, firstRow)) {
			return results;
		}

		while (true) {
			// call the object mapper for this row and add to the results
			results.add(objectMapper.createObjectFromResultSet(resultSet));

			// if this is paged, limit the number of rows loaded
			if (maxRows != 0 && results.size() == maxRows) {
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
	 *            number of rows we need to skip
	 * @return returns true if there are more rows left to process, or false if
	 *         the end of the result set was reached
	 * @throws SQLException
	 */
	private boolean skipRows(ResultSet resultSet, int firstRow)
			throws SQLException {
		int index = 0;
		while (index < firstRow && resultSet.next()) {
			index++;
		}
		return resultSet.next();
	}
}
