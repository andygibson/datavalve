package org.jdataset.impl.provider;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.provider.DataProvider;

public abstract class AbstractDataProvider<T> implements DataProvider<T>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private Class<?> entityClass;
	
	public abstract Integer fetchResultCount();

	public abstract List<T> fetchResults(Paginator paginator);


	public Class<?> getEntityClass() {
		if (entityClass == null) {

			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			entityClass = (Class<?>) type.getActualTypeArguments()[0];
		}
		return entityClass;
	}
}
