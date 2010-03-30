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

package org.fluttercode.spigot.wicket;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.DefaultPaginator;
import org.fluttercode.spigot.Paginator;

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
