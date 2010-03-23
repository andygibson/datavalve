package org.jdataset.sample.wicket.custom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.provider.jdbc.AbstractJdbcDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.phonelist.model.Person;

public class JdbcCustomPaginatorPage extends AbstractCustomPaginatorPage {

	public JdbcCustomPaginatorPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {			
		Connection connection = getWicketApp().getConnection();
		
        QueryDataProvider<Person> people = new AbstractJdbcDataProvider<Person>(connection) {

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
        return new Dataset<Person>(people);        
	}
	

}
