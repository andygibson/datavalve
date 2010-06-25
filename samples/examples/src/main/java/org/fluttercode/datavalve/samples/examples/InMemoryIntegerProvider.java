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
import java.util.Comparator;
import java.util.List;

import org.fluttercode.datavalve.provider.InMemoryDataProvider;

/**
 * @author Andy Gibson
 *
 */
public class InMemoryIntegerProvider extends InMemoryDataProvider<Integer>
		implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private class IntegerSorter implements Comparator<Integer>, Serializable {

		private static final long serialVersionUID = 1L;
		
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}
	}

	public InMemoryIntegerProvider() {
		getOrderKeyMap().put("default", new IntegerSorter());
	}

	@Override
	protected List<Integer> fetchBackingData() {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 1; i <= 100; i++) {
			result.add(i);
		}
		return result;
	}
}
