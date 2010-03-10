package org.jdataset.samples.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jdataset.DataProvider;
import org.jdataset.Paginator;

public class IntegerDataProvider implements DataProvider<Integer>,Serializable {

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
