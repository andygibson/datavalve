package org.jdataset.sample.wicket.custom;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.phonelist.model.Person;

public class JpaCustomPaginatorPage extends AbstractCustomPaginatorPage {

	public JpaCustomPaginatorPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
		
        JpaDataProvider<Person> people = new JpaDataProvider<Person>();
        people.getStatementHandler().setCountStatement("select count(p) from Person p");
        people.getStatementHandler().setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        return new Dataset<Person>(people);
	}
	

}
