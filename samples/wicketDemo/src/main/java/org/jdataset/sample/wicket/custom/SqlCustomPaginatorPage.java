package org.jdataset.sample.wicket.custom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.ObjectDataset;
import org.jdataset.sql.AbstractSqlDataset;
import org.jdataset.sql.SqlDataset;
import org.phonelist.model.Person;

public class SqlCustomPaginatorPage extends AbstractCustomPaginatorPage {

	public SqlCustomPaginatorPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {			
		Connection connection = getWicketApp().getConnection();
        SqlDataset<Person> people = new AbstractSqlDataset<Person>(connection) {

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
        
        people.setCountStatement("select count(1) from PERSONS p");
        people.setSelectStatement("select * from PERSONS p");        
        return people;
	}
	

}
