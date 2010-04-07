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

package org.fluttercode.spigot.provider;

import java.util.Collections;
import java.util.List;

import org.fluttercode.spigot.Paginator;
import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.AbstractQueryDataProvider;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;
import org.fluttercode.spigot.provider.util.DataQuery;

/**
 * @author Andy Gibson
 * 
 */
public class DummyDataProvider<T> extends AbstractQueryDataProvider<T> {


	private static final long serialVersionUID = 1L;

	public DummyDataProvider() {
		addParameterResolver(new ParameterResolver() {

			public boolean resolveParameter(
					ParameterizedDataProvider<? extends Object> dataset,
					Parameter parameter) {

				if (":VALUE1".equals(parameter.getName())) {
					parameter.setValue(1l);
					return true;
				}

				if (":VALUE2".equals(parameter.getName())) {
					parameter.setValue(2l);
					return true;
				}

				if (":NULL".equals(parameter.getName())) {
					parameter.setValue(null);
					return true;
				}

				if ("#{id}".equals(parameter.getName())) {
					parameter.setValue(99);
					return true;
				}

				return false;
			}

			public boolean acceptParameter(String name) {
				return name.startsWith(":") || name.startsWith("#{");
			}
		});
	}

	@Override
	protected Integer queryForCount(DataQuery query) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<T> queryForResults(DataQuery query, Integer firstResult,
			Integer count) {
		return Collections.EMPTY_LIST;
	}

	@Override
	protected DataQuery buildDataQuery(String baseStatement,
			boolean includeOrdering, Paginator paginator) {
		return new DataQuery();
	}
}
