/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.params;

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
