package org.jdataset.sample.wicket.search;

import org.apache.wicket.PageParameters;
import org.jdataset.combined.ParameterizedDataset;
import org.jdataset.impl.combo.DefaultParameterizedDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.jdataset.impl.provider.jpa.JpaDataset;
import org.jdataset.provider.QueryDataProvider;
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
        provider.getRestrictions().add("upper(p.firstName) like upper(:firstNameValue)");
        provider.getRestrictions().add("upper(p.lastName) like upper(:lastNameValue)");
        provider.getRestrictions().add("p.phone like :phoneValue");
        provider.getRestrictions().add("p.id = :id");
        provider.setEntityManager(getWicketApp().createEntityManager());
        provider.getOrderKeyMap().put("id","p.id");
        provider.getOrderKeyMap().put("name","p.lastName,p.firstName");
        return new DefaultParameterizedDataset<Person>(provider);
	}

}
