/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.dataset.QueryDataset;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.provider.jdbc.AbstractJdbcDataProvider;

/**
 * @author Andy Gibson
 * 
 */
public class SqlQueryDatasetTest extends BaseJdbcDatasetTest<Person> {

	private static final long serialVersionUID = 1L;
	private QueryDataset<Person> dataset;

	@Override
	public ObjectDataset<Person> buildObjectDataset() {
		return dataset;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dataset = createDataset();
	}

	protected QueryDataset<Person> createDataset() {

		QueryDataProvider<Person> provider = new AbstractJdbcDataProvider<Person>(
				getConnection()) {

			private static final long serialVersionUID = 1L;

			@Override
			public Person createObjectFromResultSet(ResultSet resultSet)
					throws SQLException {
				return new Person(resultSet.getLong(1), resultSet.getString(2),
						resultSet.getString(3));

			}
		};
		provider.setSelectStatement("select * from persons");
		provider.setCountStatement("select count(1) from persons");
		provider.getOrderKeyMap().put("id", "id");
		provider.getOrderKeyMap().put("name", "last_name,first_name");

		return new QueryDataset<Person>(provider);
	}

	public void testParameterQuery() {
		dataset.getProvider().getRestrictions().add("FIRST_NAME like 'A%'");
		int count = 0;
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("A"));
			count++;
		}
		assertEquals(count, dataset.getResultCount().intValue());
	}

	public void testParameterQueryWithValue() {
		dataset.getProvider().getRestrictions().add("FIRST_NAME like :param");
		dataset.getProvider().getParameters().put("param", "S%");
		int count = 0;
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("S"));
			count++;
		}

		assertEquals(count, dataset.getResultCount().intValue());
	}

	public void testParameterQueryWithValueReRead() {

		dataset.getProvider().getRestrictions().add("FIRST_NAME like :param");
		dataset.getProvider().getParameters().put("param", "M%");
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("M"));
		}

		dataset.getProvider().getParameters().put("param", "T%");
		dataset.refresh();
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("T"));
		}

	}

	public void testParameterQueryWithNoValue() {
		dataset.getProvider().getRestrictions().add("FIRST_NAME like :param");
		assertEquals(getDataRowCount(), dataset.getResultCount().intValue());
		int count = dataset.getResultCount();
		assertEquals(getDataRowCount(), count);
	}

	public void testParameterizedQueryCount() {
		dataset.getProvider().getRestrictions().add("FIRST_NAME like :param");
		dataset.getProvider().getParameters().put("param", "%");
		assertEquals(getDataRowCount(), dataset.getResultCount().intValue());
	}

	public void testOrdering() {
		dataset.setOrderKey("name");
		String last = null;
		String test = null;
		for (Person person : dataset) {
			test = person.getLastName() + " " + person.getFirstName();
			if (last != null) {
				assertTrue(last.compareTo(test) < 0);
			}
			last = test;
		}
	}

}
