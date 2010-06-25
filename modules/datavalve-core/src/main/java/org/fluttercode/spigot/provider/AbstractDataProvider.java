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
 * Abstract class that implements the {@link DataProvider} interface. The fetch
 * methods from this interface are implemented and in turn call other methods
 * that can be overriden.
 * <p/>
 * This class also introduces the {@link AbstractDataProvider#doPreFetch()}
 * method that is called before either the results or the result count is
 * fetched. This lets you prep any state on the provider prior to execution (for
 * example, setting up restrictions).
 * <p/>
 * Subclasses to this class implement the data provider interface for a specific
 * data access mechanism by overriding the
 * {@link AbstractDataProvider#doFetchResults(Paginator)} and the
 * {@link AbstractDataProvider#doFetchResultCount()} methods to return the data
 * and the total count of available results.
 * <p/>
 * Developers using those
 * 
 * @author Andy Gibson
 * 
 */
/**
 * @author GIBSOA01
 *
 * @param <T>
 */
public abstract class AbstractDataProvider<T> implements DataProvider<T>,
		Serializable {

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

	
	public Integer fetchResultCount() {
		doPreFetch();
		return doPostFetchResultCount(doFetchResultCount());
	}

	protected Integer doPostFetchResultCount(Integer resultCount) {
		return resultCount;
	}

	public List<T> fetchResults(Paginator paginator) {
		doPreFetch();
		return doPostFetchResults(doFetchResults(paginator), paginator);
	}

	/**
	 * Provides a hook to examine the result list prior to returning them back
	 * to the user. By default, this method just returns the list of results
	 * passed in.
	 * 
	 * @param results
	 *            List of results
	 * @param paginator
	 *            paginator that was used to fetch the results
	 * @return the list of results to be sent back to the user.
	 */
	protected List<T> doPostFetchResults(List<T> results, Paginator paginator) {
		return results;
	}

	/**
	 * Gives developers in subclasses the opportunity to alter the provider
	 * state prior to querying for data. This is called before the result count
	 * and result list fetches are executed.
	 * <p/>
	 * For example, you could alter parameter values or query restrictions prior
	 * to each fetch.By default, this method does nothing.
	 */
	protected void doPreFetch() {

	}

	/**
	 * Fetches the results for the provider. Override this method in subclasses
	 * to implement different data providers. To perform additional processing
	 * prior to the fetch, override the
	 * {@link AbstractDataProvider#doPreFetch()} method.
	 * 
	 * @param paginator
	 *            Pagination info for the results to be returned
	 * @return List of result objects
	 */
	protected abstract List<T> doFetchResults(Paginator paginator);

	/**
	 * Fetches the number of results available. Override this method in
	 * subclasses to implements different provider implementations. To perform
	 * additional processing prior to the fetch override the
	 * {@link AbstractDataProvider#doPreFetch()} method.
	 * 
	 * @return The number of results
	 */
	protected abstract Integer doFetchResultCount();
}
