package org.jdataset.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdataset.IObjectDataset;
import org.jdataset.combo.IQueryDataset;
import org.jdataset.combo.QueryDataset;
import org.jdataset.provider.IQueryDataProvider;

public class SqlQueryDatasetTest extends BaseJdbcDatasetTest<Person> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public IObjectDataset<Person> buildObjectDataset() {
		return createDataset();
	}

	public IQueryDataset<Person> createDataset() {

		IQueryDataProvider<Person> provider = new AbstractJdbcQueryDataProvider<Person>(
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

	public void testParameterizedQuery() {
		IQueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like 'A%'");
		int count = 0;
		for (Person p : ds) {
			assertTrue(p.getFirstName().startsWith("A"));
			count++;
		}
		assertEquals(count, ds.getResultCount().intValue());		
	}
	
	public void testParameterizedQueryWithValue() {
		IQueryDataset<Person> ds = createDataset();
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
		IQueryDataset<Person> ds = createDataset();
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
		IQueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like :param");
		assertEquals(getDataRowCount(), ds.getResultCount().intValue());
		int count = ds.getResultCount();
		assertEquals(getDataRowCount(), count);	
	}
	
	public void testParameterizedQueryCount() {
		IQueryDataset<Person> ds = createDataset();
		ds.getRestrictions().add("FIRST_NAME like :param");
		ds.getParameters().put("param","%");
		assertEquals(getDataRowCount(), ds.getResultCount().intValue());							
	}
	
	public void testOrdering() {
		IQueryDataset<Person> ds = createDataset();
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
