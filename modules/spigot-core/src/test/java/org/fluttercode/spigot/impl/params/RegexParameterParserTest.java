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

package org.fluttercode.spigot.impl.params;

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
