package org.jdataset.sample.wicket.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.DefaultParameterizedDataset;
import org.jdataset.dataset.ParameterizedDataset;
import org.jdataset.impl.provider.jdbc.AbstractJdbcQueryDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.phonelist.model.Person;

public class SqlSearchPage extends AbstractSearchPage {

	public SqlSearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ParameterizedDataset<Person> createDataset() {
		Connection connection = getWicketApp().getConnection();
		QueryDataProvider<Person> provider = new AbstractJdbcQueryDataProvider<Person>(
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
		
		provider.getStatementHandler().setCountStatement("select count(1) from PERSONS p");
		provider.getStatementHandler().setSelectStatement("select * from PERSONS p");
		provider.getRestrictionHandler().add(
				"upper(p.first_Name) like upper(:firstNameValue)");
		provider.getRestrictionHandler().add(
				"upper(p.last_Name) like upper(:lastNameValue)");
		provider.getRestrictionHandler().add("p.phone like :phoneValue");
		provider.getRestrictionHandler().add("p.id = :id");
		provider.getOrderHandler().add("id", "p.id");
		provider.getOrderHandler().add("name", "p.last_Name,p.first_Name");
		return new DefaultParameterizedDataset<Person>(provider);
	}

}
