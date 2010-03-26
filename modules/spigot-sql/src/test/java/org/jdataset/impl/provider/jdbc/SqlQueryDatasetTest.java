/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.jdataset.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdataset.dataset.ObjectDataset;
import org.jdataset.dataset.QueryDataset;
import org.jdataset.provider.QueryDataProvider;

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
		dataset.getRestrictions().add("FIRST_NAME like 'A%'");
		int count = 0;
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("A"));
			count++;
		}
		assertEquals(count, dataset.getResultCount().intValue());
	}

	public void testParameterQueryWithValue() {
		dataset.getRestrictions().add("FIRST_NAME like :param");
		dataset.getParameters().put("param", "S%");
		int count = 0;
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("S"));
			count++;
		}

		assertEquals(count, dataset.getResultCount().intValue());
	}

	public void testParameterQueryWithValueReRead() {

		dataset.getRestrictions().add("FIRST_NAME like :param");
		dataset.getParameters().put("param", "M%");
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("M"));
		}

		dataset.getParameters().put("param", "T%");
		dataset.refresh();
		for (Person p : dataset) {
			assertTrue(p.getFirstName().startsWith("T"));
		}

	}

	public void testParameterQueryWithNoValue() {
		dataset.getRestrictions().add("FIRST_NAME like :param");
		assertEquals(getDataRowCount(), dataset.getResultCount().intValue());
		int count = dataset.getResultCount();
		assertEquals(getDataRowCount(), count);
	}

	public void testParameterizedQueryCount() {
		dataset.getRestrictions().add("FIRST_NAME like :param");
		dataset.getParameters().put("param", "%");
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
