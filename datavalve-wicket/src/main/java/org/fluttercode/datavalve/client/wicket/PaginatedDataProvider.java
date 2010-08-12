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

package org.fluttercode.datavalve.client.wicket;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.fluttercode.datavalve.DataProvider;
import org.fluttercode.datavalve.DefaultPaginator;
import org.fluttercode.datavalve.Paginator;

/**
 * @author Andy Gibson
 * 
 */
public class PaginatedDataProvider<T> extends SortableDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private DataProvider<T> provider;
	private Integer size;

	public Iterator<? extends T> iterator(int first, int count) {
		Paginator paginator = new DefaultPaginator();
		paginator.setFirstResult(first);
		paginator.setMaxRows(count);
		return provider.fetchResults(paginator).iterator();
	}

	public IModel<T> model(T object) {
		return new CompoundPropertyModel<T>(object);
	}

	public int size() {
		if (size == null) {
			size = provider.fetchResultCount();
		}
		return size;
	}

}
