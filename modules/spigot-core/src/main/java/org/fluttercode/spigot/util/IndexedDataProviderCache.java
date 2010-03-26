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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;

/**
 * Implementation of a data cache that is used to keep hold of a limited set of
 * result values, and uses a LRU mechanism to eject older items. This is designed
 * for use with random access mechanisms for accessing data by index. it can
 * perform a look ahead prefetch so the next set of results are probably loaded
 * in.
 * <p>
 * The {@link IndexedDataProviderCache#loadValues(Integer, int)} method is used as a template
 * to load the values with a look ahead. 
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <K>
 *            Key type used for the map
 * @param <V>
 *            Value type used in the map
 */
public class IndexedDataProviderCache<V> {

	private final DataProvider<V> provider;
	private final Paginator paginator;
	private final int maximumSize;

	private Map<Integer, V> map;

	public IndexedDataProviderCache(DataProvider<V> provider, Paginator paginator) {
		this(provider, paginator, 100, 25);
	}

	public IndexedDataProviderCache(DataProvider<V> provider, Paginator paginator,
			int maxEntries, int initialSize) {

		this.provider = provider;
		this.maximumSize = maxEntries;
		this.paginator = paginator;

		map = new LinkedHashMap<Integer, V>(initialSize, 0.75f, true) {
			protected boolean removeEldestEntry(Map.Entry<Integer, V> eldest) {
				return size() > maximumSize;
			};
		};

	}

	public V get(Integer key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		List<V> values = loadValues(key, 10);

		if (values == null || values.size() == 0) {
			return null;
		}

		for (int i = 0; i < values.size(); i++) {
			map.put(key + i, values.get(i));
		}
		return values.get(0);
	}

	protected void put(Integer key, V value) {
		map.put(key, value);
	}

	protected void putAll(Map<Integer, V> map) {
		map.putAll(map);
	}

	/**
	 * Load the item indicated by the key and return it.
	 * 
	 * @param key
	 *            Key of the value to load
	 * @return the item to load
	 */
	protected List<V> loadValues(Integer key, int batchSize) {
		// load ahead
		paginator.setFirstResult(key);
		paginator.setMaxRows(10);
		return getProvider().fetchResults(paginator);
	}

	public DataProvider<V> getProvider() {
		return provider;
	}

	public void clear() {
		map.clear();
	}

}
