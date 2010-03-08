package org.jdataset.jsfdemo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdataset.dataset.QueryDataset;
import org.jdataset.jsfdemo.model.Person;

@Named("personSearch")
@RequestScoped
public class PersonSearchDataset extends QueryDataset<Person> {

	private static final long serialVersionUID = 1L;

	public PersonSearchDataset() {		
		super();		
	}

	@Inject
	public PersonSearchDataset(PersonSearchProvider provider) {
		super(provider);
		setMaxRows(10);
	}

	@Override
	@Produces
	@Named("personResults")
	public List<Person> getResultList() {
		return super.getResultList();

	}
}
