package org.jdataset.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdataset.AbstractParameterizedDataset;
import org.jdataset.ObjectDataset;
import org.jdataset.Parameter;
import org.jdataset.QueryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for Query driven datasets that implements most of the methods
 * in the {@link QueryDataset} interface. It adds features for defining
 * select/count statements, and translating orderKey values into other
 * representations. It also defines common methods for defining an order clause
 * and building statements based on the Sql/Ejbql structure.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains.
 */
public abstract class AbstractQueryDataset<T> extends
		AbstractParameterizedDataset<T> implements QueryDataset<T>,
		Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory
			.getLogger(AbstractQueryDataset.class);

	private static Pattern commaSplitter = Pattern.compile(",");

	private String selectStatement;
	private String countStatement;
	private Map<String, String> orderKeyMap = new HashMap<String, String>();
	private List<String> restrictions = new ArrayList<String>();
	private boolean nextAvailable;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdataset.db.QueryDset#getOrderKeyMap()
	 */
	public Map<String, String> getOrderKeyMap() {
		return orderKeyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdataset.db.QueryDset#setOrderKeyMap(java.util.Map)
	 */
	public void setOrderKeyMap(Map<String, String> orderKeyMap) {
		this.orderKeyMap = orderKeyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdataset.db.QueryDset#getRestrictions()
	 */
	public List<String> getRestrictions() {
		return restrictions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdataset.db.QueryDset#setRestrictions(java.util.List)
	 */
	public void setRestrictions(List<String> restrictions) {
		this.restrictions = restrictions;
	}

	/**
	 * Determines the order by clause based on the values of
	 * {@link ObjectDataset#getOrderKey()},
	 * {@link QueryDataset#getOrderKeyMap()} and
	 * {@link QueryDataset#isOrderAscending()}. If no order is specified then
	 * <code>null</code> is returned.
	 * 
	 * @see #calculateOrderByClause()
	 * 
	 * @return The order by statements or null of there is no order by
	 *         specified.
	 */
	public final String calculateOrderBy() {
		String orderKey = getOrderKey();
		// fail quickly if we don't have an order
		if (orderKey == null || orderKey.length() == 0) {
			return null;
		}
		if (orderKeyMap.size() == 0) {
			log
					.warn("orderKey property is set but there are no values in the orderKeyMap.");
			return null;
		}
		String order = getOrderKeyMap().get(orderKey);
		if (order == null) {
			log.warn("orderKey value '{}' not found in orderKeyMap", orderKey);
			return null;
		}

		// parse out fields and add order
		String[] fields = commaSplitter.split(order);
		order = "";
		// concatenate the fields with the direction, put commas in between
		for (String field : fields) {
			if (order.length() != 0) {
				order = order + ", ";
			}
			order = order + field + (isOrderAscending() ? " ASC " : " DESC ");
		}
		log.debug("Order key = {}, order by = {}", getOrderKey(), order);
		return order;
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
	public final String calculateOrderByClause() {
		String order = calculateOrderBy();
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
			Map<String, Object> queryParams, boolean includeOrderBy) {
		queryParams.clear();
		RestrictionBuilder rb = new RestrictionBuilder(this);

		for (Parameter param : rb.getParameterList()) {
			queryParams.put(param.getName(), param.getValue());
		}

		String result = baseStatement + rb.buildWhereClause();
		if (includeOrderBy) {
			result = result + calculateOrderByClause();
		}
		log.debug("Built statement : '{}'", result);
		return result;
	}

	@Override
	protected final List<T> fetchResults() {

		// fetch maxrows+1 so we can see if we have more results to fetch
		Integer count = includeAllResults() ? 0 : getMaxRows() + 1;
		List<T> temp = fetchResultsFromDatabase(count);

		// if we returned more than maxRows, then we have more data to fetch
		nextAvailable = temp.size() > getMaxRows() && !includeAllResults();
		if (includeAllResults() || temp.size() < getMaxRows()) {
			return temp;
		}

		// create a sublist containing maxRows number of rows.
		return temp.subList(0, getMaxRows());
	}

	public boolean isNextAvailable() {
		// check we have results loaded since this sets the nextAvailable flag
		checkResultsLoaded();
		return nextAvailable;
	}

	/**
	 * Method to check that the results are loaded, usually just fetches the
	 * results, but may in future require more processing.
	 */
	protected void checkResultsLoaded() {
		// just call getResults to load them if they aren't already loaded.
		getResults();
	}

	/**
	 * @param count
	 *            Number of rows to return
	 * @return The results from the database based on the select statement and
	 *         restrictions
	 */
	protected abstract List<T> fetchResultsFromDatabase(Integer count);

	public void addRestriction(String restriction) {
		getRestrictions().add(restriction);
	}

}
