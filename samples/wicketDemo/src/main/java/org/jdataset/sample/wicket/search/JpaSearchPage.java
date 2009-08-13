package org.jdataset.sample.wicket.search;

import org.apache.wicket.PageParameters;
import org.jdataset.ParameterizedDataset;
import org.jdataset.db.jpa.JpaDataset;
import org.phonelist.model.Person;

public class JpaSearchPage extends AbstractSearchPage {

	public JpaSearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ParameterizedDataset<Person> createDataset() {
        JpaDataset<Person> people = new JpaDataset<Person>();
        people.setCountStatement("select count(p) from Person p");
        people.setSelectStatement("select p from Person p");
        people.getRestrictions().add("upper(p.firstName) like upper(:firstNameValue)");
        people.getRestrictions().add("upper(p.lastName) like upper(:lastNameValue)");
        people.getRestrictions().add("p.phone like :phoneValue");
        people.getRestrictions().add("p.id = :id");
        people.setEntityManager(getWicketApp().createEntityManager());
        people.getOrderKeyMap().put("id","p.id");
        people.getOrderKeyMap().put("name","p.lastName,p.firstName");
        return people;
	}

}
