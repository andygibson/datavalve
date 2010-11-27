/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.samples.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fluttercode.datavalve.DataProvider;
import org.fluttercode.datavalve.Paginator;

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

		while (start <= end) {
			results.add(start++);			
		}
		paginator.setNextAvailable(end < 100);
		return results;
	}

}
