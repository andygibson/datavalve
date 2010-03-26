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

package org.jdataset.text;

import org.jdataset.util.CommaDelimitedProvider;


/**
 * @author Andy Gibson
 * 
 */
public class PhoneTextFile extends CommaDelimitedProvider<PhoneEntry> {

	private static final long serialVersionUID = 1L;

	public PhoneTextFile(String fileName) {
		super(fileName);
	}

	@Override
	protected PhoneEntry createObjectFromColumns(String[] columns) {

		return new PhoneEntry(Integer.valueOf(columns[0]), columns[1],
				columns[2], columns[3]);
	}

}
