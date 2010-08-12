/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.provider;

import java.util.List;

import org.fluttercode.datavalve.Paginator;
import org.fluttercode.datavalve.provider.util.DataQuery;

/**
 * Base class for any data provider that is driven by a Query Language. This
 * class implements the fundamentals of generating a {@link DataQuery} object
 * that can then be used to query data in the different query language providers
 * (Hql, Ejbql, Sql etc).
 * <p>
 * By using an internal query to hold the statement and the parameters, we can
 * utilize that in different ways in different sub classes for different data
 * access mechanisms
 * 
 * @author Andy Gibson
 * 
 */
public abstract class AbstractQLDataProvider<T> extends
		AbstractParameterizedDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private String selectStatement;
	private String countStatement;

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

	@Override
	protected Integer doFetchResultCount() {
		DataQuery query = buildDataQuery(getCountStatement(), false, null);
		return queryForCount(query);
	}

	@Override
	protected List<T> doFetchResults(Paginator paginator) {
		// fetch the results by building the data query and handing it off to a
		// query execution method.
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

	/**
	 * Go fetch the results from the database using the defined query and first
	 * result and result size information.
	 * <p/>
	 * Override in subclasses to provide different implementations for fetching
	 * the results
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
	 * Override in subclasses to provide different implementations for fetching
	 * the count
	 * 
	 * @param query
	 * @return
	 */
	protected abstract Integer queryForCount(DataQuery query);

	/**
	 * Builds the {@link DataQuery} object from the base query statement and the
	 * provider information. Pass the {@link Paginator} for order information as
	 * well as a flag indicating whether ordering is included. We don't want
	 * ordering on count statements so make sure it is false for those.
	 * <p>
	 * This method is all about taking the provider statements and restrictions
	 * and assembling the final query including parameterizing the restrictions
	 * and adding the order by clause if needed.
	 * <p/>
	 * Override in subclasses to provide different implementations for
	 * generating the query, althought a default implementation is provided in
	 * the {@link AbstractQueryDataProvider} which most Query Language based
	 * providers should subclass.
	 * <p/>
	 * In most final subclasses for JPA, SQL, Hibernate etc, you should just
	 * need to implement the <code>queryForCount</code> and
	 * <code>queryForResults</code> methods since these are specific to the
	 * actual data connectivity mechanism whereas more general code has been
	 * pulled up into parent classes to be inherited.
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
