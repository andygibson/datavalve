package org.jdataset.sample.wicket.search;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.DefaultParameterizedDataset;
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
        provider.getStatementHandler().setCountStatement("select count(p) from Person p");
        provider.getStatementHandler().setSelectStatement("select p from Person p");
        provider.getRestrictionHandler().add("upper(p.firstName) like upper(:firstNameValue)");
        provider.getRestrictionHandler().add("upper(p.lastName) like upper(:lastNameValue)");
        provider.getRestrictionHandler().add("p.phone like :phoneValue");
        provider.getRestrictionHandler().add("p.id = :id");
        provider.setEntityManager(getWicketApp().createEntityManager());
        provider.getOrderHandler().add("id","p.id");
        provider.getOrderHandler().add("name","p.lastName,p.firstName");
        return new DefaultParameterizedDataset<Person>(provider);
	}

}
