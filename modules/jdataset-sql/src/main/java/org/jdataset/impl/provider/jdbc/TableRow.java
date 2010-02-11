package org.jdataset.impl.provider.jdbc;

import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class is used to return the results in the mapped sql dataset without
 * having to create a class to return the results in. The results are returned
 * in this class as a map of values. To access a field value, just call
 * {@link TableRow#getValue(String)} and pass in the name of the field to
 * access. The lookup is case sensitive.
 * </p>
 * 
 * @author Andy Gibson
 * 
 * 
 */
public class TableRow {

	private static Logger log = LoggerFactory.getLogger(TableRow.class);
	
	/**
	 * The <code>values</code> map holds the values
	 */
	private Map<String, Object> values = new HashMap<String, Object>();

	/**
	 * Helper method to add a new value into the row
	 * 
	 * @param column
	 *            Name of the column to add
	 * @param value
	 *            Value of the column to add
	 */
	public void add(String column, Object value) {
		values.put(column, value);
	}

	/**
	 * Returns the value of the passed in column name for the row in the
	 * {@link ResultSet} this object represents.
	 * 
	 * @param columnName
	 *            name of the field to return
	 * @return the value of that column for this row.
	 */
	public Object getValue(String columnName) {
		if (values.containsKey(columnName)) {
			return values.get(columnName);
		}
		throw new IllegalArgumentException(String.format(
				"Dataset does not contain field '%s'", columnName));
	}

	/**
	 * A static factory method that creates and initializes a TableRow instance
	 * for the current row of the passed in {@link ResultSet}.
	 * 
	 * @param resultSet
	 *            The {@link ResultSet} containing the values we need to load
	 *            into the TableRow instance.
	 * @return a new TableRow instance initialized with the values from the
	 *         {@link ResultSet}.
	 */
	public static TableRow createTableRowFromResultSet(ResultSet resultSet) {
		TableRow result = new TableRow();
		try {
			ResultSetMetaData metadata = resultSet.getMetaData();
			for (int i = 0; i < metadata.getColumnCount(); i++) {
				int colIndex = i + 1;
				String name = metadata.getColumnName(colIndex);
				Object value = resultSet.getObject(colIndex);
				result.add(name, value);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return result;
	}	
}
