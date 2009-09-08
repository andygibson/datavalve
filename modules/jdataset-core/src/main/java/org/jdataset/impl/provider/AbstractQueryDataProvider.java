package org.jdataset.impl.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdataset.ObjectDataset;
import org.jdataset.Paginator;
import org.jdataset.impl.RestrictionBuilder;
import org.jdataset.params.Parameter;
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
		AbstractParameterizedDataProvider<T> implements QueryDataProvider<T>,
		Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory
			.getLogger(AbstractQueryDataProvider.class);

	private static Pattern commaSplitter = Pattern.compile(",");
	private String selectStatement;
	private String countStatement;
	private Map<String, String> orderKeyMap = new HashMap<String, String>();
	private List<String> restrictions = new ArrayList<String>();	

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

	public Map<String, String> getOrderKeyMap() {
		return orderKeyMap;
	}

	public void setOrderKeyMap(Map<String, String> orderKeyMap) {
		this.orderKeyMap = orderKeyMap;
	}

	public List<String> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<String> restrictions) {
		this.restrictions = restrictions;
	}

	/**
	 * Determines the order by clause based on the values of
	 * {@link ObjectDataset#getOrderKey()},
	 * {@link QueryDataProvider#getOrderKeyMap()} and
	 * {@link QueryDataProvider#isOrderAscending()}. If no order is specified then
	 * <code>null</code> is returned.
	 * 
	 * @see #calculateOrderByClause()
	 * 
	 * @return The order by fields with asc/desc indicators or null of there is
	 *         no order by specified.
	 */
	public final String calculateOrderBy(Paginator paginator) {
		String orderKey = paginator.getOrderKey();

		// fail quickly if we don't have an order
		if (orderKey == null || orderKey.length() == 0) {
			return null;
		}

		// we specified an order key, but we have no values, so issue a warning
		// to the user
		if (orderKeyMap.size() == 0) {
			log.warn("orderKey property is set but the orderKeyMap is empty.");
			return null;
		}

		String order = translateOrderKey(orderKey);
		if (order == null) {
			// if we can't find the order, then warn the user
			log.warn("orderKey value '{}' not translated successfully",
					orderKey);
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
			order = order + field + (paginator.isOrderAscending() ? " ASC " : " DESC ");
		}
		log.debug("Order key = {}, order by = {}", paginator.getOrderKey(), order);
		return order;
	}

	private String translateOrderKey(String orderKeyValue) {
		return getOrderKeyMap().get(orderKeyValue);
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
		String order = calculateOrderBy(paginator);
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
			Map<String, Object> queryParams, boolean includeOrderBy,Paginator paginator) {
		queryParams.clear();
		RestrictionBuilder rb = new RestrictionBuilder(this);

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
		Integer count = paginator.includeAllResults() ? 0 : paginator.getMaxRows() + 1;
		List<T> temp = fetchResultsFromDatabase(paginator,count);

		// if we returned more than maxRows, then we have more data to fetch
		boolean nextAvailable = temp.size() > paginator.getMaxRows() && !paginator.includeAllResults();
		paginator.setNextAvailable(nextAvailable);
		if (paginator.includeAllResults() || temp.size() < paginator.getMaxRows()) {
			return temp;
		}
		

		// create a sublist containing maxRows number of rows.
		return temp.subList(0, paginator.getMaxRows());
	}

	/**
	 * @param count
	 *            Number of rows to returnm, if 0, return all rows.
	 * @return The results from the database based on the select statement and
	 *         restrictions
	 */
	protected abstract List<T> fetchResultsFromDatabase(Paginator paginator,Integer count);

	public void addRestriction(String restriction) {
		getRestrictions().add(restriction);
	}

}
