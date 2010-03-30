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

package org.fluttercode.spigot.impl.provider;

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
