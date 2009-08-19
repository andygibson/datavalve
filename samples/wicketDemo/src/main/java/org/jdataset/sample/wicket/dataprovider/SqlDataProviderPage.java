package org.jdataset.sample.wicket.dataprovider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.ObjectDataset;
import org.jdataset.QueryDataset;
import org.jdataset.sql.AbstractJdbcQueryDataset;
import org.phonelist.model.Person;

public class SqlDataProviderPage extends AbstractDataProviderPage {

	public SqlDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
		
		Connection connection = getWicketApp().getConnection();
        QueryDataset<Person> people = new AbstractJdbcQueryDataset<Person>(connection) {

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
		people.getOrderKeyMap().put("id", "p.ID");
		people.getOrderKeyMap().put("name", "p.LAST_NAME,p.FIRST_NAME");
		people.getOrderKeyMap().put("phone", "p.PHONE");
		return people;
	}

}
