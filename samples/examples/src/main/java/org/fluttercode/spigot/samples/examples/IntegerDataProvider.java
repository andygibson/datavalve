/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
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
