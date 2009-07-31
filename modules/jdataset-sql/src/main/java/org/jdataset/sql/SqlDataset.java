package org.jdataset.sql;

import org.jdataset.ParameterizedDataset;

/**
 * Interface for datasets that use parameterized SQL to get their results from
 * the database. This interface extends the {@link ParameterizedDataset}
 * interface and adds methods for setting and getting count and select SQL
 * statements.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface SqlDataset<T> extends ParameterizedDataset<T> {

	public String getCountStatement();

	public void setCountStatement(String countStatement);

	public String getSelectStatement();

	public void setSelectStatement(String selectStatement);

}
