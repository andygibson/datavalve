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

package org.fluttercode.spigot.impl.provider.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.fluttercode.spigot.Parameter;
import org.fluttercode.spigot.provider.AbstractQueryDataProvider;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.provider.util.DataQuery;

/**
 * Base class for a JPA based {@link QueryDataProvider}. Override and implement
 * {@link #createJpaQuery(String)} to create a query of the type needed.
 * 
 * @see JpaDataProvider
 * @see JpaNativeProvider
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public abstract class AbstractJpaDataProvider<T> extends
		AbstractQueryDataProvider<T> implements JpaDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;

	public AbstractJpaDataProvider() {		
	}
	
	public AbstractJpaDataProvider(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	/**
	 * Override to create the specific type of query to use.
	 * 
	 * @see JpaDataProvider
	 * @see JpaNativeProvider
	 * 
	 * @param ql
	 *            Statement the query must execute (could be EJBQL or Native
	 *            depending on subclass)
	 * @return Query object created from the
	 *         {@link AbstractJpaDataProvider#entityManager} and configured with
	 *         the passed in sql.
	 */
	protected abstract Query createJpaQuery(String ql);

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Initializes a JPA {@link Query} using the passed in {@link DataQuery}.
	 * The type of query returned is determined from the
	 * {@link AbstractJpaDataProvider#createJpaQuery(String)} method which can
	 * return a native or EJBQL query depending on the subclass.
	 * 
	 * @param dataQuery
	 *            The {@link DataQuery} to initialize the query with
	 * @return The initialized {@link Query}
	 */
	private final Query buildJpaQuery(DataQuery dataQuery) {
		Query qry = createJpaQuery(dataQuery.getStatement());
		for (Parameter param : dataQuery.getParameters()) {
			qry.setParameter(param.getName(), param.getValue());
		}
		return qry;
	}

	@Override
	protected Integer queryForCount(DataQuery query) {
		Query qry = buildJpaQuery(query);
		Long result = (Long) qry.getSingleResult();
		return new Integer(result.intValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<T> queryForResults(DataQuery query, Integer firstResult,
			Integer count) {
		Query qry = buildJpaQuery(query);
		if (firstResult != null) {
			qry.setFirstResult(firstResult.intValue());
		}

		if (count != null) {
			qry.setMaxResults(count);
		}

		return qry.getResultList();
	}
}
