package org.jdataset.jsfdemo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.event.AjaxBehaviorEvent;
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

	public void test() {
		changeOrderKey("id");
		System.out.println("order key = " + getOrderKey());
		System.out.println("order asc = " + isOrderAscending());
	}

	public void testInt(int id) {
		System.out.println("Passing " + id);
		changeOrderKey("name");
	}

	public String change(String value) {
		changeOrderKey(value);
		return null;
	}

	public String toggle() {
		changeOrderKey("id");
		return null;
	}

	public void testIt(int value) {
		System.out.println("Running testIt with value " + value);
	}

	public void update(AjaxBehaviorEvent event) {
		refresh();
	}

}
