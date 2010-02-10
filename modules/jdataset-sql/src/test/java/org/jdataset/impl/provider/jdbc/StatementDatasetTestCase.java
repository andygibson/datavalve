package org.jdataset.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.jdataset.dataset.DefaultStatementDataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.dataset.StatementDataset;
import org.jdataset.provider.ParameterizedDataProvider;
import org.jdataset.provider.StatementDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatementDatasetTestCase extends BaseJdbcDatasetTest<TableRow> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory
			.getLogger(StatementDatasetTestCase.class);

	private StatementDataset<TableRow> createDataset() {
		MappedJdbcDataProvider result = new MappedJdbcDataProvider(getConnection());
		result.setSelectStatement("select * from TestValues order by id");
		result.setCountStatement("select count(1) from TestValues");
		
		return new DefaultStatementDataset<TableRow>(result);
	}

	public void testRecordCount() {
		StatementDataset<TableRow> qry = createDataset();		
		assertEquals(100, qry.getResultCount().intValue());
	}

	public void testResultsFirstPage() {
		StatementDataset<TableRow> qry = createDataset();
		qry.setMaxRows(10);
		assertEquals(100, qry.getResultCount().intValue());
		List<TableRow> results = qry.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		// check values

		for (int i = 0; i < 10; i++) {
			assertNotNull(results.get(i));
			assertEquals(i, results.get(i).getValue("VALUE"));
			assertEquals(i, results.get(i).getValue("ID"));
		}
	}

	public void testResultPages() {
		StatementDataset<TableRow> qry = createDataset();
		qry.setMaxRows(10);
		assertEquals(100, qry.getResultCount().intValue());
		// check values

		for (int p = 0; p < 10; p++) {
			List<TableRow> results = qry.getResultList();
			assertNotNull(results);
			assertEquals(10, results.size());

			for (int i = 0; i < 10; i++) {
				assertNotNull(results.get(i));
				int expected = i + (p * 10);
				assertEquals(expected, results.get(i).getValue("VALUE"));
				assertEquals(expected, results.get(i).getValue("ID"));
			}
			qry.next();
		}
	}

	public void testUnsetParameters() {
		StatementDataset<TableRow> qry = createDataset();
		qry.setMaxRows(10);
		qry.setSelectStatement("select * from TestValues where id = #{id}");
		List<TableRow> results = qry.getResultList();

		assertNotNull(results);
		assertEquals(0, results.size());
	}

	public void testSetParameters() {
		StatementDataset<TableRow> qry = createDataset();
		qry.setMaxRows(10);
		qry.setSelectStatement("select * from TestValues where id = :id");
		qry.addParameter("id", 4);
		List<TableRow> results = qry.getResultList();		

		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(4, results.get(0).getValue("ID"));
		assertEquals(4, results.get(0).getValue("VALUE"));
	}

	public void testParamResolver() {
		StatementDataset<TableRow> qry = createDataset();
		qry.addParameterResolver(new ParameterResolver() {

			public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,Parameter parameter) {
				if ("#{myId}".equals(parameter.getName())) {
					parameter.setValue(27);
					return true;
				}
				return false;
			}

			public boolean acceptParameter(String parameter) {
				return parameter.startsWith("#{") && parameter.endsWith("}");
			}
		});
		qry.setSelectStatement("select * from TestValues where id = #{myId}");
		List<TableRow> results = qry.getResultList();

		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(27, results.get(0).getValue("ID"));
	}

	public void testParamResolverMissingValue() {
		StatementDataset<TableRow> qry = createDataset();
		qry.addParameterResolver(new ParameterResolver() {

			public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,Parameter parameter) {
				if ("myId".equals(parameter.getName())) {
					parameter.setValue(27);
					return true;
				}
				return false;
			}

			public boolean acceptParameter(String parameter) {
				return true;
			}
		});
		qry
				.setSelectStatement("select * from TestValues where id = #{myId_unknown}");
		List<TableRow> results = qry.getResultList();

		assertNotNull(results);
		assertEquals(0, results.size());
	}

	public void testInvalidColumnName() {
		StatementDataset<TableRow> qry = createDataset();
		List<TableRow> results = qry.getResultList();
		assertNotNull(results);
		assertEquals(100, results.size());
		try {
			results.get(0).getValue("NO COLUMN, THROW EXCEPTION");
			fail("Should have thrown an unknown column name");
		} catch (IllegalArgumentException ex) {
			// we expected this
		}
	}

	public void testPagingPageSizePlusOne() {
		// test the paging when the
		StatementDataset<TableRow> qry = createDataset();
		qry.setSelectStatement("select * from TestValues where id < 21");
		qry.setCountStatement("select count(1) from TestValues where id < 21");

		assertEquals(21, qry.getResultCount().intValue());

		assertEquals(21, qry.getResultList().size());

		qry.setMaxRows(10);
		assertEquals(false, qry.isPreviousAvailable());
		assertEquals(true, qry.isNextAvailable());

		assertEquals(3, qry.getPageCount());

		List<TableRow> results = qry.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		assertEquals(false, qry.isPreviousAvailable());
		assertEquals(true, qry.isNextAvailable());

		qry.next();
		results = qry.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		assertEquals(true, qry.isPreviousAvailable());
		assertEquals(true, qry.isNextAvailable());

		qry.next();
		results = qry.getResultList();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(true, qry.isPreviousAvailable());
		assertEquals(false, qry.isNextAvailable());
	}

	public void testPagingPageSizeMinusOne() {
		log.debug("Testing size minus One");
		// test the paging when the
		StatementDataset<TableRow> qry = createDataset();
		qry.setSelectStatement("select * from TestValues where id < 19");
		qry.setCountStatement("select count(1) from TestValues where id < 19");

		assertEquals(19, qry.getResultCount().intValue());
		assertEquals(19, qry.getResultList().size());

		qry.setMaxRows(10);
		assertEquals(2, qry.getPageCount());

		List<TableRow> results = qry.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		assertEquals(false, qry.isPreviousAvailable());
		assertEquals(true, qry.isNextAvailable());

		qry.next();
		results = qry.getResultList();
		assertNotNull(results);
		assertEquals(9, results.size());
		assertEquals(true, qry.isPreviousAvailable());
		assertEquals(false, qry.isNextAvailable());
	}

	public void testObjectCreation() {

		StatementDataProvider<TestValue> provider = new AbstractJdbcDataProvider<TestValue>(
				getConnection()) {

			private static final long serialVersionUID = 1L;

			@Override
			public TestValue createObjectFromResultSet(ResultSet resultSet)
					throws SQLException {
				TestValue result = new TestValue();
				result.id = resultSet.getInt(1);
				result.value = resultSet.getInt(2);
				return result;
			}
		};
		StatementDataset<TestValue> qry = new DefaultStatementDataset<TestValue>(provider);
		qry.setCountStatement("select count(1) from TestValues");
		qry.setSelectStatement("select * from TestValues");
		qry.setMaxRows(10);
		assertEquals(100, qry.getResultCount().intValue());
		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());
		List<TestValue> results = qry.getResultList();
		assertEquals(10, results.size());

		for (int p = 0; p < 10; p++) {
			results = qry.getResultList();
			for (int i = 0; i < 10; i++) {
				TestValue val = results.get(i);
				int expected = (p * 10) + i;
				assertEquals(expected, val.id);
				assertEquals(expected, val.value);
			}
			qry.next();
		}
	}

	@Override
	public ObjectDataset<TableRow> buildObjectDataset() {
		return createDataset();
	}
}
