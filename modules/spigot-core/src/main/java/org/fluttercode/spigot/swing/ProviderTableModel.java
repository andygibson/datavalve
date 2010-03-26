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

package org.fluttercode.spigot.swing;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;
import org.fluttercode.spigot.impl.DefaultPaginator;
import org.fluttercode.spigot.util.IndexedDataProviderCache;

/**
 * Abstract class that implements a {@link TableModel} and allows for cached
 * results that can be referenced by index. The cache then fetches data in
 * batches from the provided {@link DataProvider}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object this model returns
 */
public abstract class ProviderTableModel<T> extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private IndexedDataProviderCache<T> cache;
	private final Paginator paginator = new DefaultPaginator();
	private final DataProvider<T> provider;

	public ProviderTableModel(DataProvider<T> provider) {
		super(0,0);
		this.provider = provider;
		cache = new IndexedDataProviderCache<T>(provider, paginator);
	}

	@Override
	public int getRowCount() {
		return provider == null ? 0 : provider.fetchResultCount();
	}

	@Override
	public Object getValueAt(int row, int column) {
		T object = cache.get(row);
		return getColumnValue(object, column);
	}

	protected abstract Object getColumnValue(T object, int column);

	public Paginator getPaginator() {
		return paginator;
	}

	public void invalidate() {
		cache.clear();
	}
}
