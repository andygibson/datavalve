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

import org.fluttercode.spigot.params.RegexParameterParser;

import junit.framework.TestCase;

/**
 * @author Andy Gibson
 * 
 */
public class RegexParameterParserTest extends TestCase {
	
	public void testParamFindingSimple() {
		RegexParameterParser parser = new RegexParameterParser();
		String[] params = parser.extractParameters("this is a :param and so is #{this}");
		assertEquals(2, params.length);
	}
	
	public void testParamFindingDuplicates() {
		RegexParameterParser parser = new RegexParameterParser();
		String[] params = parser.extractParameters("this is a :param and so is #{this} and again :param");
		assertEquals(3, params.length);
	}

	public void testReplaceFirstFunc() {
		String line = " value = #{id} or value = #{id} ";
		String old = "#{id}";
		old = old.replace("{", "\\{");
		old = old.replace("}", "\\}");
		String result = line.replaceFirst(old, "123");
		assertEquals(" value = 123 or value = #{id} ", result);
	}
	
	public void testError() {
		String line = "p.id = #{id}";		
		String result = line.replaceFirst("#\\{id\\}", ":param_0");
		assertEquals("p.id = :param_0", result);
	}
	
	
	public void testParamFindingManuallyAddedParams() {
		RegexParameterParser parser = new RegexParameterParser();
		String[] params = parser.extractParameters("this is a :_param_0 and should be found");
		assertEquals(1, params.length);
	}
	

}
