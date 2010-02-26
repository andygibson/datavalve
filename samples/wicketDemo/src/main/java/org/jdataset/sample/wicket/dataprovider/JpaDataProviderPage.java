package org.jdataset.sample.wicket.dataprovider;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.jdataset.impl.provider.jpa.JpaQueryProvider;
import org.phonelist.model.Person;

public class JpaDataProviderPage extends AbstractDataProviderPage {

	public JpaDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
        JpaDataProvider<Person> people = new JpaQueryProvider<Person>();
        people.setCountStatement("select count(p) from Person p");
        people.setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        people.getOrderKeyMap().put("id", "p.id");
        people.getOrderKeyMap().put("name", "p.lastName,p.firstName");
        people.getOrderKeyMap().put("phone", "p.phone");
        return new Dataset<Person>(people);
	}

}
