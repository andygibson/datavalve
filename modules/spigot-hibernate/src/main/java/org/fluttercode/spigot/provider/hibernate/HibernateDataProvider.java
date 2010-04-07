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

package org.fluttercode.spigot.provider.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.AbstractQueryDataProvider;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.provider.util.DataQuery;

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
