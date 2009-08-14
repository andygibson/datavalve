package org.jdataset.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdataset.ObjectDataset;
import org.jdataset.QueryDataset;

public class SqlQueryDatasetTest extends BaseSqlDatasetTest<Person> {

	@Override
	public ObjectDataset<Person> buildObjectDataset() {
		return createDataset();
	}

	public QueryDataset<Person> createDataset() {

		QueryDataset<Person> qry = new AbstractSqlQueryDataset<Person>(
				getConnection()) {

			@Override
			public Person createObjectFromResultSet(ResultSet resultSet)
					throws SQLException {
				return new Person(resultSet.getLong(1), resultSet.getString(2),
						resultSet.getString(3));

			}
		};
		qry.setSelectStatement("select * from persons");
		qry.setCountStatement("select count(1) from persons");
		qry.getOrderKeyMap().put("id", "id");
		qry.getOrderKeyMap().put("name", "last_name,first_name");
		return qry;
	}

	public void testParameterizedQuery() {
		QueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like 'A%'");
		int count = 0;
		for (Person p : ds) {
			assertTrue(p.getFirstName().startsWith("A"));
			count++;
		}
		assertEquals(count, ds.getResultCount().intValue());		
	}
	
	public void testParameterizedQueryWithValue() {
		QueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like :param");
		ds.getParameters().put("param","S%");
		int count = 0;
		for (Person p : ds) {
			assertTrue(p.getFirstName().startsWith("S"));
			count++;
		}
		
		assertEquals(count, ds.getResultCount().intValue());
	}
	
	public void testParameterizedQueryWithValueReRead() {
		QueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like :param");
		ds.getParameters().put("param","M%");		
		for (Person p : ds) {
			assertTrue(p.getFirstName().startsWith("M"));
		}
		
		ds.getParameters().put("param","T%");		
		ds.refresh();
		for (Person p : ds) {
			assertTrue(p.getFirstName().startsWith("T"));
		}
		
	}
	
	public void testParameterizedQueryWithNoValue() {
		QueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like :param");
		assertEquals(getDataRowCount(), ds.getResultCount().intValue());
		int count = ds.getResultCount();
		assertEquals(getDataRowCount(), count);	
	}
	
	public void testParameterizedQueryCount() {
		QueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like :param");
		ds.getParameters().put("param","%");
		assertEquals(getDataRowCount(), ds.getResultCount().intValue());							
	}
	
	public void testOrdering() {
		QueryDataset<Person> ds = createDataset();
		ds.setOrderKey("name");
		String last = null;
		String test = null;
		for (Person person : ds) {
			test = person.getLastName()+" " +person.getFirstName();
			if (last != null) {				
				assertTrue(last.compareTo(test) < 0);
			}
			last = test;
		}
	}
	
	
}
