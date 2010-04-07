/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;

/**
 * @author Andy Gibson
 * 
 */
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
