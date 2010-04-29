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

package org.fluttercode.spigot.samples.cdidemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.fluttercode.spigot.el.ExpressionParameterResolver;
import org.fluttercode.spigot.provider.hibernate.HibernateDataProvider;
import org.fluttercode.spigot.samples.cdidemo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andy Gibson
 * 
 */
@RequestScoped
public class PersonSearchProvider extends HibernateDataProvider<Person> {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory
			.getLogger(PersonSearchProvider.class);

	public PersonSearchProvider() {
		super();
	}

	@Inject
	public PersonSearchProvider(Session session) {
		log.debug("Creating {}", PersonSearchProvider.class);
		setSession(session);
		init(Person.class, "p");
		getOrderKeyMap().put("id", "p.id");
		getOrderKeyMap().put("name", "p.lastName,p.firstName");
		getOrderKeyMap().put("phone", "p.phone");
		addParameterResolver(new ExpressionParameterResolver());
		addRestriction("p.firstName like #{searchCriteria.firstNameWildcard}");
		addRestriction("upper(p.lastName) like #{searchCriteria.lastNameWildcard}");
		addRestriction("p.phone like #{searchCriteria.phoneWildcard}");
	}

}
