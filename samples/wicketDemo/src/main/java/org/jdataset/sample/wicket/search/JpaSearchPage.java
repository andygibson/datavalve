package org.jdataset.sample.wicket.search;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.ParameterizedDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.phonelist.model.Person;

public class JpaSearchPage extends AbstractSearchPage {

	public JpaSearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ParameterizedDataset<Person> createDataset() {
        JpaDataProvider<Person> provider = new JpaDataProvider<Person>();
        provider.setCountStatement("select count(p) from Person p");
        provider.setSelectStatement("select p from Person p");
        provider.addRestriction("upper(p.firstName) like upper(:firstNameValue)");
        provider.addRestriction("upper(p.lastName) like upper(:lastNameValue)");
        provider.addRestriction("p.phone like :phoneValue");
        provider.addRestriction("p.id = :id");
        provider.setEntityManager(getWicketApp().createEntityManager());
        provider.getOrderKeyMap().put("id","p.id");
        provider.getOrderKeyMap().put("name","p.lastName,p.firstName");
        return new ParameterizedDataset<Person>(provider);
	}

}
