package org.jdataset.provider.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.jdataset.IPaginator;
import org.jdataset.provider.IDataProvider;

public abstract class AbstractDataProvider<T> implements IDataProvider<T>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private Class<?> entityClass;
	
	public abstract Integer fetchResultCount();

	public abstract List<T> fetchResults(IPaginator paginator);


	public Class<?> getEntityClass() {
		if (entityClass == null) {

			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			entityClass = (Class<?>) type.getActualTypeArguments()[0];
		}
		return entityClass;
	}
}
