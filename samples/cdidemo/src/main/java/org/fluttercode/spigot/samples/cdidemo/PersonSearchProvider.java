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

package org.fluttercode.spigot.samples.cdidemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.fluttercode.spigot.impl.provider.hibernate.HibernateDataProvider;
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
