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
