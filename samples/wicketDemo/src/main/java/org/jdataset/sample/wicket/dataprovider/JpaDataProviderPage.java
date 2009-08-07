package org.jdataset.sample.wicket.dataprovider;

import org.apache.wicket.PageParameters;
import org.jdataset.ObjectDataset;
import org.jdataset.db.jpa.JpaDataset;
import org.phonelist.model.Person;

public class JpaDataProviderPage extends AbstractDataProviderPage {

	public JpaDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
        JpaDataset<Person> people = new JpaDataset<Person>();
        people.setCountStatement("select count(p) from Person p");
        people.setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        people.getOrderKeyMap().put("id", "p.id");
        people.getOrderKeyMap().put("name", "p.lastName,p.firstName");
        people.getOrderKeyMap().put("phone", "p.phone");
        return people;
	}

}
