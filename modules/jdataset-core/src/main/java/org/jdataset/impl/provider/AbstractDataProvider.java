package org.jdataset.impl.provider;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.jdataset.DataProvider;

public abstract class AbstractDataProvider<T> implements DataProvider<T>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private Class<?> entityClass;

	public Class<?> getEntityClass() {
		if (entityClass == null) {

			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			entityClass = (Class<?>) type.getActualTypeArguments()[0];
		}
		return entityClass;
	}
}
