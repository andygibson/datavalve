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

package org.fluttercode.spigot.impl.provider.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.fluttercode.spigot.Parameter;
import org.fluttercode.spigot.impl.provider.DataQuery;
import org.fluttercode.spigot.provider.AbstractQueryDataProvider;
import org.fluttercode.spigot.provider.QueryDataProvider;

/**
 * A Hibernate based {@link QueryDataProvider}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public class HibernateDataProvider<T> extends AbstractQueryDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private Session session;

	public HibernateDataProvider() {
		super();
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {		
		this.session = session;
	}

	/**
	 * Initializes a hibernate {@link Query} for this {@link DataQuery} by
	 * setting the statement and the parameters.
	 * 
	 * @param query The {@link DataQuery} we want to base the {@link Query} off.
	 * @return An initialized {@link Query} instance.
	 */
	private Query buildHibernateQuery(DataQuery query) {
		Query qry = getSession().createQuery(query.getStatement());

		// set the parameters from the internal parameter list
		for (Parameter parameter : query.getParameters()) {
			qry.setParameter(parameter.getName(), parameter.getValue());
		}
		return qry;
	}

	@Override
	protected Integer queryForCount(DataQuery query) {
		Query qry = buildHibernateQuery(query);
		Long result = (Long) qry.uniqueResult();
		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<T> queryForResults(DataQuery query, Integer firstResult,
			Integer count) {

		Query qry = buildHibernateQuery(query);

		if (count != null) {
			qry.setMaxResults(count.intValue());
		}

		if (firstResult != null) {
			qry.setFirstResult(firstResult.intValue());
		}

		return qry.list();
	}
}
