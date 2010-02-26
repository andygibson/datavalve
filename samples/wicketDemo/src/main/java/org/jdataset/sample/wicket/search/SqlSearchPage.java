package org.jdataset.sample.wicket.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.ParameterizedDataset;
import org.jdataset.impl.provider.jdbc.AbstractJdbcDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.phonelist.model.Person;

public class SqlSearchPage extends AbstractSearchPage {

	public SqlSearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ParameterizedDataset<Person> createDataset() {
		Connection connection = getWicketApp().getConnection();
		QueryDataProvider<Person> provider = new AbstractJdbcDataProvider<Person>(
				connection) {

			@Override
			public Person createObjectFromResultSet(ResultSet resultSet)
					throws SQLException {
				Person person = new Person();
				person.setId(resultSet.getLong(1));
				person.setFirstName(resultSet.getString(2));
				person.setLastName(resultSet.getString(3));
				person.setPhone(resultSet.getString(4));
				return person;
			}
		};
		
		provider.setCountStatement("select count(1) from PERSONS p");
		provider.setSelectStatement("select * from PERSONS p");
		provider.addRestriction(
				"upper(p.first_Name) like upper(:firstNameValue)");
		provider.addRestriction(
				"upper(p.last_Name) like upper(:lastNameValue)");
		provider.addRestriction("p.phone like :phoneValue");
		provider.addRestriction("p.id = :id");
		provider.getOrderKeyMap().put("id", "p.id");
		provider.getOrderKeyMap().put("name", "p.last_Name,p.first_Name");
		return new ParameterizedDataset<Person>(provider);
	}

}
