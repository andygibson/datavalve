package org.jdataset.sample.wicket.custom;

import org.apache.wicket.PageParameters;
import org.jdataset.ObjectDataset;
import org.jdataset.db.jpa.JpaDataset;
import org.phonelist.model.Person;

public class JpaCustomPaginatorPage extends AbstractCustomPaginatorPage {

	public JpaCustomPaginatorPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
        JpaDataset<Person> people = new JpaDataset<Person>();
        people.setCountStatement("select count(p) from Person p");
        people.setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        return people;
	}
	

}
