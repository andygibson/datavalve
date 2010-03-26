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

package org.fluttercode.spigot.util;

/**
 * @author Andy Gibson
 * 
 */
public abstract class CommaDelimitedProvider<T> extends TextFileProvider<T>{

	public CommaDelimitedProvider(String filename) {
		super(filename);
	}
	
	
	@Override
	protected T createObjectFromLine(String line) {
		String[] columns = line.split(",");
		return createObjectFromColumns(columns);
	}

	protected abstract T createObjectFromColumns(String[] columns);
}
