package org.jdataset.sample.wicket.dataprovider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.provider.jdbc.AbstractJdbcQueryDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.phonelist.model.Person;

public class SqlDataProviderPage extends AbstractDataProviderPage {

	public SqlDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
		
		Connection connection = getWicketApp().getConnection();
        QueryDataProvider<Person> provider = new AbstractJdbcQueryDataProvider<Person>(connection) {

			private static final long serialVersionUID = 1L;

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
		provider.getOrderKeyMap().put("id", "p.ID");
		provider.getOrderKeyMap().put("name", "p.LAST_NAME,p.FIRST_NAME");
		provider.getOrderKeyMap().put("phone", "p.PHONE");
		return new Dataset<Person>(provider);
	}

}
