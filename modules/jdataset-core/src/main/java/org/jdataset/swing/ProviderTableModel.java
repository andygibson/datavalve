package org.jdataset.swing;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jdataset.DataProvider;
import org.jdataset.Paginator;
import org.jdataset.impl.DefaultPaginator;
import org.jdataset.util.IndexedDataProviderCache;

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
