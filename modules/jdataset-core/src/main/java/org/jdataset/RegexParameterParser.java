package org.jdataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParameterParser implements ParameterParser,Serializable {
	
	private Pattern pattern;

	protected Pattern getPattern() {
		if (pattern == null) {
			pattern = Pattern.compile(getRegEx());
		}
		return pattern;
	}
	
	private String getRegEx() {
		return ":[A-Za-z0-9]+|#\\{.+}";
	}

	public String[] extractParameters(String line) {
		List<String> parameters = new ArrayList<String>();
		String[] results = new String[0];
		
		Matcher matcher = getPattern().matcher(line);
		while (matcher.find()) {
			parameters.add(matcher.group());
		}

		// if we have params, push them into the array, otherwise return the
		// empty array we created
		if (parameters.size() != 0) {
			results = parameters.toArray(results);
		}
		return results;
	}

}