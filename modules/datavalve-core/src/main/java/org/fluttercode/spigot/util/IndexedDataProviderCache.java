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

package org.fluttercode.spigot.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;

/**
 * Implementation of a data cache that is used to keep hold of a limited set of
 * result values, and uses a LRU mechanism to eject older items. This is
 * designed for use with random access mechanisms for accessing data by index.
 * it can perform a look ahead prefetch so the next set of results are probably
 * loaded in.
 * <p>
 * The {@link IndexedDataProviderCache#loadValues(Integer, int)} method is used
 * as a template to load the values with a look ahead.
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <V>
 *            Value type used in the map
 */
public class IndexedDataProviderCache<V> {

	private final DataProvider<V> provider;
	private final Paginator paginator;
	private final int maximumSize;

	private Map<Integer, V> map;

	public IndexedDataProviderCache(DataProvider<V> provider,
			Paginator paginator) {
		this(provider, paginator, 100, 25);
	}

	public IndexedDataProviderCache(DataProvider<V> provider,
			Paginator paginator, int maxEntries, int initialSize) {

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
