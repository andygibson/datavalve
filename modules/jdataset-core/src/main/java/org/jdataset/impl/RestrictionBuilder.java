package org.jdataset.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdataset.impl.params.ParameterParser;
import org.jdataset.impl.params.ParameterValues;
import org.jdataset.impl.params.RegexParameterParser;
import org.jdataset.params.Parameter;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processes the list of restrictions, and builds the final list of restrictions
 * and the list of parameters needed for the restrictions. Parameters may be
 * listed in order as they appear or accessible as a map. The final restrictions
 * also have the parameter expressions replaced with parameter names used in the
 * query.
 * <p>
 * Parameter processing involves the following :
 * <ul>
 * <li>Iterating through the restrictions</li>
 * <li>For each restriction, extract the parameters</li>
 * <li>For each parameter in the restriction, get the value, if the value is
 * null, exclude that restriction unless <code>includeMissingParameters</code>
 * is set.</li>
 * <li>If this restriction is included then :
 * <ul>
 * <li>Rename each parameter</li>
 * <li>Replace the param name in the restriction with the new name (or ? )</li>
 * <li>Add the parameter with the new name into the final parameter list</li>
 * <li>Add the restriction with the renamed params into the final restrictions</li>
 * </ul>
 * </ul>
 * At the end, we should be able to get the list of finalized restrictions with
 * renamed parameters from the restrictions and parameterList properties. We
 * should be able to access the parameters either by name, or sequentially.
 * 
 * @author Andy Gibson
 * 
 */
public class RestrictionBuilder implements Serializable {

	private static final long serialVersionUID = 1L;

	private ParameterParser parameterParser = new RegexParameterParser();

	private Pattern logicalOpPattern = Pattern.compile(
			"\\A[ (]*\\b(and|or)\\b[ (]*.*", Pattern.CASE_INSENSITIVE);

	private static Logger log = LoggerFactory
			.getLogger(RestrictionBuilder.class);

	/**
	 * Determines how the final parameters appear in the final where clause,
	 * whether they are named parameters appearing as ':paramName' or whether
	 * they are assigned based on order and only appear in the query as a
	 * question mark '?'.
	 * 
	 * @author Andy Gibson
	 * 
	 */
	public enum ParameterStyle {
		/**
		 * In the final restriction, make parameters appear as a name prefixed
		 * with a colon. i.e. <code>:parameterName1</code>
		 */
		NAMED_PARAMETERS,
		/**
		 * In the final restriction, make parameters appear as a single
		 * quotation mark. '<code>?</code>'
		 */
		ORDERED_QUESTION_MARKS

	}

	private List<String> restrictions = new ArrayList<String>();
	private List<Parameter> parameterList = new ArrayList<Parameter>();
	private QueryDataProvider<? extends Object> dataset;
	private int paramId;
	private ParameterStyle parameterStyle = ParameterStyle.NAMED_PARAMETERS;
	private boolean includeMissingParameters = false;

	/**
	 * Constructs a RestrictionBuilder for this particular dataset and defaults
	 * the parameter style to <code>NAMED_PARAMETERS</code>
	 * 
	 * @param dataset
	 *            Dataset that contains the restrictions and parameter
	 *            resolution for our query
	 */
	public RestrictionBuilder(QueryDataProvider<? extends Object> dataset) {
		this(dataset, ParameterStyle.NAMED_PARAMETERS);
	}

	public RestrictionBuilder(QueryDataProvider<? extends Object> dataset,
			ParameterStyle parameterStyle) {
		super();
		this.dataset = dataset;
		this.parameterStyle = parameterStyle;
		refresh();
	}

	public void clear() {
		restrictions.clear();
		parameterList.clear();
	}

	/**
	 * Generates a unique parameter name for use in the query.
	 * 
	 * @return unique parameter name
	 */
	protected String getNewParamName() {
		paramId++;
		return "param__" + paramId;
	}

	/**
	 * Returns a parameter name to replace the parameter expression in the
	 * query. If this dataset can use named parameters, then we return a unique
	 * named parameter. Otherwise, we return a question mark '?' for use with
	 * ordered parameters where they are referenced by the order they appear.
	 * 
	 * @return the new parameter name
	 */
	protected String getReplacementParameterName() {
		switch (parameterStyle) {
		case NAMED_PARAMETERS:
			return getNewParamName();
		case ORDERED_QUESTION_MARKS:
			return "?";
		default:
			throw new RuntimeException("Unexpected Parameter Style : "
					+ parameterStyle);
		}
	}

	public ParameterStyle getParameterStyle() {
		return parameterStyle;
	}

	public void setParameterStyle(ParameterStyle parameterStyle) {
		this.parameterStyle = parameterStyle;
	}

	/**
	 * Returns the restrictions that are to be included in the query. This will
	 * be a subset of or equal to the list of restrictions on the dataset. If
	 * <code>includeMissingParameters</code> is set then the restrictions will
	 * match.
	 * 
	 * @return List of restrictions
	 */
	public List<String> getRestrictions() {
		return restrictions;
	}

	public List<Parameter> getParameterList() {
		return parameterList;
	}

	/**
	 * <p>
	 * Executes the restriction processing for the dataset passed in to the
	 * constructor. This method will reset the results, process each restriction
	 * including evaluating parameters, and excluding restrictions with null
	 * parameters (optional).
	 * </p>
	 * <p>
	 * This method is called by default on construction, and can be called to
	 * refresh the results.
	 * </p>
	 */
	public void refresh() {
		paramId = 0;
		clear();
		if (dataset == null) {
			return;
		}

		for (String restriction : dataset.getRestrictions()) {
			processRestriction(restriction);
		}
	}

	/**
	 * Process this restriction by getting the parameter expressions, evaluating
	 * them and adding the restriction to the list of restrictions along with
	 * the evaluated parameters. Optionally, the restriction may be included
	 * even if it contains null parameters.
	 * 
	 * @param restriction
	 *            The restriction to process
	 */
	protected final void processRestriction(String restriction) {

		log.debug("processing restriction '{}'", restriction);

		// if this is a null or empty string, just return
		if (restriction == null || restriction.length() == 0) {
			return;
		}
		// get the parameter expressions in this line
		String[] expressions = extractExpressions(restriction);

		// if there are no expressions, just add the restriction and leave
		if (expressions.length == 0) {
			log.debug("Found no restrictions, exiting");
			restrictions.add(restriction);
		}

		// build the parameters for this restriction line
		ParameterValues params = buildParameterList(expressions);

		// are we missing some parameter values? If so we are done if we are not
		// including missing parameters
		if (params.hasNullParameters() && !includeMissingParameters) {
			return;
		}

		// Process each parameter
		for (Parameter param : params) {

			// calcualte new name for parameter
			String newName = getReplacementParameterName();

			// calculate the replacement Name - ':paramName' or '?'
			String replacementName = getReplacementParameterPrefix() + newName;

			// replace the param in the restriction with the new name
			restriction = restriction.replace(param.getName(), replacementName);

			// set the new name of the parameter in the parameter info
			param.setName(newName);

			// add the parameter to the final list
			parameterList.add(param);
		}
		// finally, add the restriction to the list
		log.debug("Adding restriction {}", restriction);
		restrictions.add(restriction);
	}

	/**
	 * Takes a list of string parameter expressions and resolves them, storing
	 * the results in the {@link ParameterValues} list.
	 * 
	 * @param expressions
	 *            List of expressions to resolve
	 * @return List of resolved parameter values.
	 */
	private ParameterValues buildParameterList(String[] expressions) {

		ParameterValues results = new ParameterValues();

		for (String expression : expressions) {
			// resolve the value
			Object value = dataset.resolveParameter(expression);
			log.debug("adding parameter expression '{}'", expression);
			results.add(expression, value);
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
					"Parameter parser is null in restriction builder");
		}
		return parameterParser.extractParameters(restriction);
	}

	/**
	 * Returns the where clause based on the restrictions in the passed in
	 * dataset and the resolved parameters. Returns a blank string if there are
	 * no restrictions.
	 * 
	 * @return the Query where statement prefixed with ' WHERE ' or a blank
	 *         string
	 */
	public String buildWhereClause() {

		StringBuilder sb = new StringBuilder();

		for (String s : restrictions) {
			if (sb.length() != 0) {
				if (!startsWithLogicalOperator(s)) {
					sb.append("AND ");
				}
			}
			sb.append(s);
			sb.append(" ");
		}
		if (sb.length() != 0) {
			sb.insert(0, " WHERE ");
		}

		return sb.toString();
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

	public ParameterParser getParameterParser() {
		return parameterParser;
	}

	public void setParameterParser(ParameterParser parameterParser) {
		this.parameterParser = parameterParser;
	}

	/**
	 * Returns a prefix that is prepended to the new parameter name prior to
	 * replacement. This is used for prefixing the colon ':' to the parameter
	 * name for named parameters. For ordered params that use '?' as the
	 * paramter marker, we don't use a prefix so we return an empty string;
	 * 
	 * @return Prefix that is prepended to the new parameter name
	 */
	protected String getReplacementParameterPrefix() {
		if (parameterStyle == ParameterStyle.NAMED_PARAMETERS) {
			return ":";
		}
		return "";
	}
}
