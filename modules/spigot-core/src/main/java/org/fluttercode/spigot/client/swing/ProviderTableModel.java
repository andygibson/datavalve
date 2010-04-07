/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.client.swing;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.DefaultPaginator;
import org.fluttercode.spigot.Paginator;
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
