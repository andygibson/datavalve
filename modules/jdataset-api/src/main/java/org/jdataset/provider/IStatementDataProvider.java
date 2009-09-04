package org.jdataset.provider;



/**
 * Interface for datasets that use parameterized SQL to get their results from
 * the database. This interface extends the {@link IParameterizedDataProvider}
 * interface and adds methods for setting and getting count and select SQL
 * statements.<br/>
 * This is different to a {@link IQueryDataProvider} in that the dataset doesn't
 * handle the ordering or restrictions. The query is a fixed SQL statement that
 * is parameterized.
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface IStatementDataProvider<T> extends IParameterizedDataProvider<T> {

	public String getCountStatement();

	public void setCountStatement(String countStatement);

	public String getSelectStatement();

	public void setSelectStatement(String selectStatement);

}
