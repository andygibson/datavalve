package org.jdataset.impl.provider;

import java.util.List;

import org.jdataset.Paginator;

/**
 * Base class for any data provider that is driven by a Query Language. This
 * class implements the fundamentals of generating a {@link DataQuery} object
 * that can then be used to query data in the different query language providers
 * (Hql, Ejbql, Sql etc).
 * <p>
 * By using an internal query to hold the statement and the parameters, we can
 * utilize that in different ways in different sub classes.
 * 
 * @author Andy Gibson
 * 
 */
public abstract class AbstractQLDataProvider<T> extends
		AbstractParameterizedDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private String selectStatement;
	private String countStatement;

	@Override
	public Integer fetchResultCount() {
		DataQuery query = buildDataQuery(getCountStatement(), false, null);
		return queryForCount(query);
	}

	@Override
	public List<T> fetchResults(Paginator paginator) {
		// fetch the results by building the data query and handing it off to a
		// query executor.
		DataQuery query = buildDataQuery(getSelectStatement(), true, paginator);

		Integer count = paginator.includeAllResults() ? null : paginator
				.getMaxRows() + 1;
		List<T> temp = queryForResults(query, paginator.getFirstResult(), count);

		// if we returned more than maxRows, then we have more data to fetch
		boolean nextAvailable = (!paginator.includeAllResults())
				&& temp.size() > paginator.getMaxRows();
		paginator.setNextAvailable(nextAvailable);
		if (paginator.includeAllResults()
				|| temp.size() < paginator.getMaxRows()) {
			return temp;
		}

		// create a sublist containing maxRows number of rows.
		return temp.subList(0, paginator.getMaxRows());

	}

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

	/**
	 * Go fetch the results from the database using the defined query and first
	 * result and result size information.
	 * 
	 * 
	 * @param query
	 *            The {@link DataQuery} instance containing the statement and
	 *            parameters
	 * @param firstResult
	 *            indicates the first row that is to be returned or null if we
	 *            are fetching from the first row.
	 * @param count
	 *            the number of rows to return or null if we are to return them
	 *            all.
	 * @return List of data
	 */
	protected abstract List<T> queryForResults(DataQuery query,
			Integer firstResult, Integer count);

	/**
	 * Query for the actual result count value using the information defined in
	 * the {@link DataQuery} reference passed in. In most cases, the statement
	 * needs executing and the count value retrieved from the query.
	 * <p/>
	 * Override in subclasses to provide different implementations.
	 * 
	 * @param query
	 * @return
	 */
	protected abstract Integer queryForCount(DataQuery query);

	/**
	 * Builds the {@link DataQuery} object from the base statement and the
	 * provider information. We pass the paginator for order information as well
	 * as a flag indicating whether ordering is included. We dont' want ordering
	 * on count statements so make sure it is false for those.
	 * <p>
	 * This method is all about taking the provider statements and restrictions
	 * and
	 * 
	 * @param baseStatement
	 *            Initial statement to use for selecting data
	 * @param includeOrdering
	 *            indicates whether the order clause should be added to the
	 *            final statement
	 * @param paginator
	 *            Paginator for determining the order clause.
	 * @return {@link DataQuery} reference that contains the final QL and
	 *         parameters for obtaining the data.
	 */
	protected abstract DataQuery buildDataQuery(String baseStatement,
			boolean includeOrdering, Paginator paginator);

}
