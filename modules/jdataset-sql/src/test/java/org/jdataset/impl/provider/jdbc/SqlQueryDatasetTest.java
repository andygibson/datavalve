package org.jdataset.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdataset.ObjectDataset;
import org.jdataset.combined.QueryDataset;
import org.jdataset.impl.combo.DefaultQueryDataset;
import org.jdataset.impl.provider.jdbc.AbstractJdbcQueryDataProvider;
import org.jdataset.provider.QueryDataProvider;

public class SqlQueryDatasetTest extends BaseJdbcDatasetTest<Person> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public ObjectDataset<Person> buildObjectDataset() {
		return createDataset();
	}

	public QueryDataset<Person> createDataset() {

		QueryDataProvider<Person> provider = new AbstractJdbcQueryDataProvider<Person>(
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
		
		return new DefaultQueryDataset<Person>(provider);
	}

	public void testParameterQuery() {
		QueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like 'A%'");
		int count = 0;
		for (Person p : ds) {
			assertTrue(p.getFirstName().startsWith("A"));
			count++;
		}
		assertEquals(count, ds.getResultCount().intValue());		
	}
	
	public void testParameterQueryWithValue() {
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
	
	public void testParameterQueryWithValueReRead() {
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
	
	public void testParameterQueryWithNoValue() {
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
