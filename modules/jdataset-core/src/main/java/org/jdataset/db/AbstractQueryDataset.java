package org.jdataset.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdataset.AbstractParameterizedDataset;
import org.jdataset.Parameter;
import org.jdataset.QueryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for Database Query driven datasets. This class handles
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public abstract class AbstractQueryDataset<T> extends AbstractParameterizedDataset<T>
		implements QueryDataset<T> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(AbstractQueryDataset.class);
	
	private String selectStatement;
	private String countStatement;
	private Map<String, String> orderKeyMap = new HashMap<String, String>();
	private List<String> restrictions = new ArrayList<String>();
	private String orderKey;
	private boolean orderAscending;
	private boolean nextAvailable;
	private Map<String, Object> queryParameters = new HashMap<String, Object>();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdataset.db.QueryDset#getOrderKey()
	 */
	public String getOrderKey() {
		return orderKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdataset.db.QueryDset#setOrderKey(java.lang.String)
	 */
	public void setOrderKey(String orderKey) {

		if (this.orderKey != null && this.orderKey.equals(orderKey)) {
			// setting it to the same value, just flip the asc/desc flag
			this.orderAscending = !this.orderAscending;
		} else {
			// set the order and reset the asc/desc order
			this.orderKey = orderKey;
			this.orderAscending = true;
		}

		invalidateResults();
	}

	public String calculateOrderBy() {

		// get the order fields from the key
		String order = orderKeyMap.get(orderKey);
		// if not set, then there is no ordering
		if (order == null) {
			return null;
		}

		// parse out fields and add order
		String[] fields = order.split(",");
		order = "";
		// concatenate the fields with the direction, put commas in between
		for (String field : fields) {
			if (order.length() != 0) {
				order = order + ", ";
			}
			order = order + field + (orderAscending ? " ASC " : " DESC ");
		}
		return order;
	}

	public String calculateOrderByClause() {
		String order = calculateOrderBy();
		if (order == null) {
			return "";
		}
		return " ORDER BY " + order;
	}

	protected final String buildStatement(String baseStatement,
			Map<String, Object> queryParams) {
		log.debug("Building statement for '{}'",baseStatement);
		queryParams.clear();
		RestrictionBuilder rb = new RestrictionBuilder(this);
		
		for (Parameter param : rb.getParameterList()) {
			queryParams.put(param.getName(), param.getValue());
		}
		
		String result = baseStatement + rb.buildWhereClause() + calculateOrderByClause();
		log.debug("Final statement : '{}'",result);
		return result;
		
	}

	@Override
	protected List<T> fetchResults() {

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

	protected Map<String, Object> getQueryParameters() {
		return queryParameters;
	}

}
