package org.jdataset.jsfdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.jdataset.impl.provider.hibernate.HibernateDataProvider;
import org.jdataset.jsfdemo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class PersonSearchProvider extends HibernateDataProvider<Person> {

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
