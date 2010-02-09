package org.jdataset.impl.provider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jdataset.OrderManager;
import org.jdataset.Paginator;
import org.jdataset.Parameter;
import org.jdataset.ParameterManager;
import org.jdataset.RestrictionManager;
import org.jdataset.StatementManager;
import org.jdataset.impl.DefaultParameterManager;
import org.jdataset.impl.DefaultQLOrderHandler;
import org.jdataset.impl.DefaultRestrictionManager;
import org.jdataset.impl.DefaultStatementManager;
import org.jdataset.impl.RestrictionBuilder;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for Query driven datasets that implements most of the methods
 * in the {@link QueryDataProvider} interface. It adds features for defining
 * select/count statements, and translating orderKey values into other
 * representations. It also defines common methods for defining an order clause
 * and building statements based on the Sql/Ejbql structure.
 *<p>
 * Typically, the orderKey translates to an order value through the OrderKeyMap.
 * However, we wrap this behaviour in the {@link #translateOrderKey(String)}
 * method. This can be overridden if you want to change how we translate
 * orderKey values.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains.
 */
public abstract class AbstractQueryDataProvider<T> extends
		AbstractDataProvider<T> implements QueryDataProvider<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory
			.getLogger(AbstractQueryDataProvider.class);

	private StatementManager statementHandler = new DefaultStatementManager();
	private DefaultQLOrderHandler orderHandler = new DefaultQLOrderHandler();
	private RestrictionManager restrictionHandler;
	private ParameterManager parameterHandler;

	public AbstractQueryDataProvider() {
		parameterHandler = new DefaultParameterManager();
		restrictionHandler = new DefaultRestrictionManager(parameterHandler);
	}

	public void init(Class<? extends Object> clazz, String prefix) {
		statementHandler.init(clazz, prefix);
	}

	/**
	 * Helper method that calculates the order expression and if not null,
	 * prefixes it with ORDER BY. If there is no order clause, it returns an
	 * empty string (not null). The result of this method can be appended to any
	 * textual query.
	 * 
	 * @return Empty string if there is no order by, otherwise, the clause
	 *         prefixed with " ORDER BY "
	 */
	public final String calculateOrderByClause(Paginator paginator) {
		String order = orderHandler.calculateOrderBy(paginator);
		if (order == null) {
			return "";
		}
		return " ORDER BY " + order;
	}

	/**
	 * Constructs a query statement based on the <code>baseStatement</code>, the
	 * restrictions, and the order values (optional). The restrictions are
	 * processed and added to the final statement if accepted and the parameters
	 * are added to the parameter list.
	 * 
	 * @see RestrictionBuilder
	 * @see #calculateOrderByClause()
	 * 
	 * @param baseStatement
	 *            The base select statement (select x from y)
	 * @param queryParams
	 *            a map to push the parameter values into
	 * @param includeOrderBy
	 *            indicates on whether to include the order clause
	 * @return
	 */
	protected final String buildStatement(String baseStatement,
			Map<String, Object> queryParams, boolean includeOrderBy,
			Paginator paginator) {
		queryParams.clear();
		RestrictionBuilder rb = new RestrictionBuilder(getRestrictionHandler());

		for (Parameter param : rb.getParameterList()) {
			queryParams.put(param.getName(), param.getValue());
		}

		String result = baseStatement + rb.buildWhereClause();
		if (includeOrderBy) {
			result = result + calculateOrderByClause(paginator);
		}
		log.debug("Built statement : '{}'", result);
		return result;
	}

	public final List<T> fetchResults(Paginator paginator) {

		// fetch maxrows+1 so we can see if we have more results to fetch
		Integer count = paginator.includeAllResults() ? 0 : paginator
				.getMaxRows() + 1;
		List<T> temp = fetchResultsFromDatabase(paginator, count);

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

	public RestrictionManager getRestrictionHandler() {
		return restrictionHandler;
	}

	public ParameterManager getParameterHandler() {
		return parameterHandler;
	}

	public OrderManager<String> getOrderHandler() {
		return orderHandler;
	}

	public StatementManager getStatementHandler() {
		return statementHandler;
	}

	/**
	 * @param count
	 *            Number of rows to returnm, if 0, return all rows.
	 * @return The results from the database based on the select statement and
	 *         restrictions
	 */
	protected abstract List<T> fetchResultsFromDatabase(Paginator paginator,
			Integer count);

}
