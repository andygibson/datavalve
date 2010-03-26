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

/**
 * Simple interface for defining a parameter parser that can take a line of a
 * query language and extract the parameters as an array of String. Any
 * implementations should be aware of the different types of parameter syntax
 * that might be used.
 * 
 * @author Andy Gibson
 * 
 */
public interface ParameterParser {

	String[] extractParameters(String line);

}
