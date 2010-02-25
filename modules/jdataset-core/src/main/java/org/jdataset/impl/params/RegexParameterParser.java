package org.jdataset.impl.params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Splits the query line based on a regular expression. Override
 * {@link #getRegEx()} to change the regex used to find parameters. The default
 * is to split based on <code>:name"</code> or <code>#{name}"</code>.
 * 
 * @author Andy Gibson
 * 
 */
public class RegexParameterParser implements ParameterParser, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pattern pattern;

	protected Pattern getPattern() {
		if (pattern == null) {
			pattern = Pattern.compile(getRegEx());
		}
		return pattern;
	}

	private String getRegEx() {
		return ":[A-Za-z0-9\\_]+|#\\{.+}";
	}

	public String[] extractParameters(String line) {
		List<String> parameters = new ArrayList<String>();		

		Matcher matcher = getPattern().matcher(line);
		while (matcher.find()) {
			parameters.add(matcher.group());
		}
		// if we have params, return them as an array
		return parameters.toArray(new String[parameters.size()]);
	}

}