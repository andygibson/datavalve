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
import java.util.Comparator;
import java.util.List;

import org.fluttercode.spigot.impl.InMemoryDataProvider;

/**
 * @author Andy Gibson
 *
 */
public class InMemoryIntegerProvider extends InMemoryDataProvider<Integer>
		implements Serializable {

	private class IntegerSorter implements Comparator<Integer>, Serializable {

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
