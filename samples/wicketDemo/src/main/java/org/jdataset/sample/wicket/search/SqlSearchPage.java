package org.jdataset.sample.wicket.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.ParameterizedDataset;
import org.jdataset.QueryDataset;
import org.jdataset.db.jpa.JpaDataset;
import org.jdataset.sql.AbstractSqlDataset;
import org.jdataset.sql.AbstractSqlQueryDataset;
import org.jdataset.sql.SqlDataset;
import org.phonelist.model.Person;

public class SqlSearchPage extends AbstractSearchPage {

	public SqlSearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ParameterizedDataset<Person> createDataset() {
		Connection connection = getWicketApp().getConnection();
		QueryDataset<Person> people = new AbstractSqlQueryDataset<Person>(connection) {

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
        
        StringBuilder statement = new StringBuilder();
        people.setCountStatement("select count(1) from PERSONS p");
        people.setSelectStatement("select * from PERSONS p");
        people.getRestrictions().add("upper(p.first_Name) like upper(:firstNameValue)");
        people.getRestrictions().add("upper(p.last_Name) like upper(:lastNameValue)");
        people.getRestrictions().add("p.phone like :phoneValue");
        people.getRestrictions().add("p.id = :id");
        people.getOrderKeyMap().put("id","p.id");
        people.getOrderKeyMap().put("name","p.last_Name,p.first_Name");
        return people;	}

}
