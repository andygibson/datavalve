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

package org.fluttercode.spigot.samples.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;

/**
 * @author Andy Gibson
 * 
 */
public class IntegerDataProvider implements DataProvider<Integer>,Serializable {

	private static final long serialVersionUID = 1L;
	
	public Integer fetchResultCount() {
		return 100;
	}

	public List<Integer> fetchResults(Paginator paginator) {
		
		int start = paginator.getFirstResult()+1; //results start from 1
		
		int end = paginator.includeAllResults() ? 100 : 
		  Math.min(100, start+paginator.getMaxRows().intValue()-1);
		
		List<Integer> results = new ArrayList<Integer>();
		System.out.println("Generating results from "+start+" to "+(end));
		while (start <= end) {
			results.add(start++);			
		}
		paginator.setNextAvailable(end < 100);
		return results;
	}

}
