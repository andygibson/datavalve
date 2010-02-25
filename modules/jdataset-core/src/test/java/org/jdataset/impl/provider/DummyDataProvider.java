package org.jdataset.impl.provider;

import java.util.Collections;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.jdataset.provider.ParameterizedDataProvider;

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
		// TODO Auto-generated method stub
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
