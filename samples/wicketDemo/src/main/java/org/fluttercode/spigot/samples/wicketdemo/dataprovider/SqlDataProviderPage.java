/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.wicketdemo.dataprovider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.fluttercode.spigot.dataset.DataProviderDataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.provider.jdbc.AbstractJdbcDataProvider;
import org.fluttercode.spigot.samples.wicketdemo.model.Person;

/**
 * @author Andy Gibson
 * 
 */
public class SqlDataProviderPage extends AbstractDataProviderPage {

	public SqlDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
		
		Connection connection = getWicketApp().getConnection();
        QueryDataProvider<Person> provider = new AbstractJdbcDataProvider<Person>(connection) {

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
		return new DataProviderDataset<Person>(provider);
	}

}
