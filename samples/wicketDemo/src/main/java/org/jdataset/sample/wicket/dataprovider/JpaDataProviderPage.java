package org.jdataset.sample.wicket.dataprovider;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.phonelist.model.Person;

public class JpaDataProviderPage extends AbstractDataProviderPage {

	public JpaDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
        JpaDataProvider<Person> people = new JpaDataProvider<Person>();
        people.getStatementHandler().setCountStatement("select count(p) from Person p");
        people.getStatementHandler().setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        people.getOrderHandler().add("id", "p.id");
        people.getOrderHandler().add("name", "p.lastName,p.firstName");
        people.getOrderHandler().add("phone", "p.phone");
        return new Dataset<Person>(people);
	}

}
