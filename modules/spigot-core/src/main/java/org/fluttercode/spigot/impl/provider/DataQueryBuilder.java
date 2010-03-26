/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.jdataset.impl.provider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdataset.Parameter;
import org.jdataset.impl.params.ParameterParser;
import org.jdataset.impl.params.ParameterValues;
import org.jdataset.impl.params.RegexParameterParser;
import org.jdataset.provider.ParameterizedDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builds a {@link DataQuery} object based on the following elements :
 * 
 * <ul>
 * <li>a <code>baseStatement</code> that defines the selection (required)</li>
 * <li>A {@link ParameterizedDataProvider} reference for evaluating parameters</li>
 * <li>an <code>orderBy</code> statement (optional)</li>
 * <li>a flag indicating if the order is ascending (only used if orderBy is set)
 * </li>
 * </ul>
 * If the {@link #provider} reference also implements the
 * {@link QueryDataProvider}, then it also processes the restrictions as part of
 * the final query.
 * <p>
 * The process involves parameterizing each line of the query (statement plus
 * restrictions) and then
 * 
 * @author Andy Gibson
 * 
 */
public class DataQueryBuilder {

	private int parameterId = 0;
	// private ParameterizedDataProvider<? extends Object> provider;
	private static Pattern commaSplitter = Pattern.compile(",");

	private StringBuilder statement = new StringBuilder();
	private StringBuilder restrictions = new StringBuilder();
	private Pattern logicalOpPattern = Pattern.compile(
			"\\A[ (]*\\b(and|or)\\b[ (]*.*", Pattern.CASE_INSENSITIVE);
	private ParameterParser parameterParser = new RegexParameterParser();

	private boolean orderedParams = false;
	private boolean allowNullParameters = false;
	private ParameterizedDataProvider<? extends Object> provider;
	private String orderBy;
	private boolean orderAscending = true;
	private String baseStatement;

	private static Logger log = LoggerFactory.getLogger(DataQueryBuilder.class);

	/**
	 * Clears the final statement and restriction values before building another
	 * query.
	 */
	private void clear() {
		statement = new StringBuilder();
		restrictions = new StringBuilder();
	}

	public DataQuery build() {

		if (provider == null) {
			throw new NullPointerException(
					"Cannot build Data Query without setting the provider");
		}

		if (baseStatement == null || baseStatement.length() == 0) {
			throw new IllegalArgumentException(
					"Cannot build Data Query without a base statement");
		}

		clear();
		DataQuery query = new DataQuery();

		addLineToQuery(parameterizeLine(baseStatement, query, true));

		// is this a query? If so, lets add the restrictions for this query
		if (provider instanceof QueryDataProvider<?>) {
			QueryDataProvider<? extends Object> queryProvider = (QueryDataProvider<? extends Object>) provider;

			for (String restriction : queryProvider.getRestrictions()) {
				addRestrictionToQuery(parameterizeLine(restriction, query,
						allowNullParameters));
			}
		}

		query.setStatement(buildFinalStatement());

		return query;
	}

	/**
	 * Process this query line by getting the parameter expressions, evaluating
	 * them. The parameters are renamed and the line is added to the query and
	 * the evaluated parameters are added to the list of parameters. Optionally,
	 * the restriction may be included even if it contains null parameters.
	 * 
	 * @param line
	 *            The query line to process
	 * 
	 * @return The statement line with the new parameter names
	 */
	protected final String parameterizeLine(String line, DataQuery query,
			boolean includeNullParameters) {

		log.debug("processing restriction '{}'", line);

		// if this is a null or empty string, just return
		if (line == null || line.length() == 0) {
			return null;
		}
		// get the parameter expressions in this line
		String[] expressions = extractExpressions(line);

		// if there are no expressions, just add the restriction and leave
		if (expressions.length == 0) {
			log.debug("Found no restrictions, exiting");
			return line;
		}

		// build the parameters for this restriction line
		ParameterValues params = buildParameterList(expressions, query);

		// are we missing some parameter values? If so we are done if we are not
		// including missing parameters
		if (params.hasNullParameters() && !includeNullParameters) {
			return null;
		}

		// Process each parameter
		for (Parameter param : params) {

			// calculate new name for parameter (param_(int) or '?')
			String newName = getNewParameterName();

			// replace the param in the restriction with the new name with the
			// necessary prefix ":" or blank if we are using ordered parameters

			String prefixedNewName = getNewParameterNamePrefix() + newName;
			log.debug("Replacing {} with {} in expression");
			String oldValue = param.getName();
			oldValue = oldValue.replace("{", "\\{");
			oldValue = oldValue.replace("}", "\\}");

			line = line.replaceFirst(oldValue, prefixedNewName);

			// set the new name of the parameter in the parameter info
			param.setName(newName);

			// add the parameter to the final list

			query.getParameters().add(param);
		}
		// finally, add the restriction to the list
		log.debug("Adding restriction {}", line);
		return line;
	}

	/**
	 * Appends a line to the final query text. Accepts a null parameter but does
	 * not add it.
	 * 
	 * @param line
	 *            Line to append to the query
	 */
	protected void addLineToQuery(String line) {
		if (line != null) {
			statement.append(line);
		}
	}

	/**
	 * Adds a restriction to the final query text. Checks to see if the line
	 * starts with a logical operator (and/or) and if not, prefixes with ' AND
	 * '.
	 * 
	 * @param line
	 *            Line to append to the query.
	 */
	protected void addRestrictionToQuery(String line) {
		if (line != null) {
			// only worry about logical operators if its not empty.
			if (restrictions.length() != 0) {
				if (!startsWithLogicalOperator(line)) {
					restrictions.append(" AND");
				}
				// add a space to separate this next statement
				restrictions.append(" ");

			}
			restrictions.append(line);
		}
	}

	/**
	 * Returns a new parameter name based on whether this query is using ordered
	 * parameters or named parameters. For ordered parameters, we always just
	 * return a "?" but with named parameters, we return a new unique name.
	 * 
	 * @return A new parameter name
	 */
	protected String getNewParameterName() {
		// return ? for jdbc type parameters
		if (orderedParams) {
			return "?";
		} else {
			return "param_" + parameterId++;
		}
	}

	/**
	 * Returns the prefix for parameters which returns a colon if it is a named
	 * parameter and an empty string if we are using ordered parameters.
	 * 
	 * @return prefix of the new parameter.
	 */
	protected String getNewParameterNamePrefix() {
		return orderedParams ? "" : ":";
	}

	/**
	 * Takes a list of string parameter expressions and resolves them, storing
	 * the results in the {@link ParameterValues} list.
	 * 
	 * @param expressions
	 *            List of expressions to resolve
	 * @return List of resolved parameter values.
	 */
	private ParameterValues buildParameterList(String[] expressions,
			DataQuery query) {

		ParameterValues results = new ParameterValues();

		if (provider != null) {
			for (String expression : expressions) {
				Object value = provider.resolveParameter(expression);
				results.add(expression, value);
			}
		}
		return results;
	}

	/**
	 * Extracts the parameter expressions contained in a query restriction into
	 * a string array. The parameters are extracted using a parameterParser
	 * which can be changed at run time.
	 * 
	 * @param restriction
	 *            the query restriction we want to extract expressions from
	 * @return a string array holding the expressions.
	 */
	protected String[] extractExpressions(String restriction) {
		if (parameterParser == null) {
			throw new IllegalStateException(
					"Parameter parser is null in query builder");
		}
		return parameterParser.extractParameters(restriction);
	}

	public ParameterParser getParameterParser() {
		return parameterParser;
	}

	public void setParameterParser(ParameterParser parameterParser) {
		this.parameterParser = parameterParser;
	}

	/**
	 * @param s
	 *            Restriction line to check
	 * @return True if the line starts with AND or OR
	 */
	public boolean startsWithLogicalOperator(String s) {
		Matcher m = logicalOpPattern.matcher(s);
		return m.matches();
	}

	/**
	 * Takes the statement and restrictions and builds a final statement which
	 * is returned to the caller.
	 * 
	 * @return The complete Query statement from the statement and restrictions.
	 */
	protected String buildFinalStatement() {
		if (restrictions.length() > 0) {
			statement.append(" WHERE ");
			statement.append(restrictions);
		}
		statement.append(buildOrderBy());
		return statement.toString();

	}

	private String buildOrderBy() {
		// parse out fields and add order
		if (orderBy == null || orderBy.length() == 0) {
			return "";
		}
		String[] fields = commaSplitter.split(orderBy);
		String order = "";
		// concatenate the fields with the direction, put commas in between
		for (String field : fields) {
			if (order.length() != 0) {
				order = order + ", ";
			}
			order = order + field + (isOrderAscending() ? " ASC" : " DESC");
		}
		return " ORDER BY " + order;
	}

	protected String buildFinalStatement(String orderBy) {

		if (orderBy != null && orderBy.length() != 0) {
			return buildFinalStatement() + " ORDER BY " + orderBy;
		}
		return buildFinalStatement();
	}

	public boolean getAllowNullParameters() {
		return allowNullParameters;
	}

	public void setAllowNullParameters(boolean allowNullParameters) {
		this.allowNullParameters = allowNullParameters;
	}

	public boolean isOrderAscending() {
		return orderAscending;
	}

	public boolean isOrderedParams() {
		return orderedParams;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setBaseStatement(String baseStatement) {
		this.baseStatement = baseStatement;
	}

	public ParameterizedDataProvider<? extends Object> getProvider() {
		return provider;
	}

	public void setProvider(ParameterizedDataProvider<? extends Object> provider) {
		this.provider = provider;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getBaseStatement() {
		return baseStatement;
	}

	public void setOrderedParams(boolean orderedParams) {
		this.orderedParams = orderedParams;
	}

	public void setOrderAscending(boolean orderAscending) {
		this.orderAscending = orderAscending;
	}

}
