/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.samples.wicketdemo.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.fluttercode.datavalve.dataset.ParameterizedDataset;
import org.fluttercode.datavalve.provider.QueryDataProvider;
import org.fluttercode.datavalve.provider.jdbc.AbstractJdbcDataProvider;
import org.fluttercode.datavalve.samples.wicketdemo.model.Person;

/**
 * @author Andy Gibson
 * 
 */
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
		provider.getOrderKeyMap().put("phone", "p.phone");
		return new ParameterizedDataset<Person>(provider);
	}

}
