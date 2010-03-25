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
import java.util.List;

import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.dataset.StatementDataset;
import org.jdataset.provider.ParameterizedDataProvider;
import org.jdataset.provider.StatementDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andy Gibson
 * 
 */
public class StatementDatasetTestCase extends BaseJdbcDatasetTest<TableRow> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory
			.getLogger(StatementDatasetTestCase.class);
	
	private StatementDataset<TableRow> dataset;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dataset = createDataset();
	}
	
	private StatementDataset<TableRow> createDataset() {
		MappedJdbcQueryDataset result = new MappedJdbcQueryDataset(getConnection());
		result.setSelectStatement("select * from TestValues order by id");
		result.setCountStatement("select count(1) from TestValues");
		
		return new StatementDataset<TableRow>(result);
	}

	public void testRecordCount() {		
		assertEquals(100, dataset.getResultCount().intValue());
	}

	public void testResultsFirstPage() {
		dataset.setMaxRows(10);
		assertEquals(100, dataset.getResultCount().intValue());
		List<TableRow> results = dataset.getResultList();
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
		dataset.setMaxRows(10);
		assertEquals(100, dataset.getResultCount().intValue());
		// check values

		for (int p = 0; p < 10; p++) {
			List<TableRow> results = dataset.getResultList();
			assertNotNull(results);
			assertEquals(10, results.size());

			for (int i = 0; i < 10; i++) {
				assertNotNull(results.get(i));
				int expected = i + (p * 10);
				assertEquals(expected, results.get(i).getValue("VALUE"));
				assertEquals(expected, results.get(i).getValue("ID"));
			}
			dataset.next();
		}
	}

	public void testUnsetParameters() {
		dataset.setMaxRows(10);
		dataset.setSelectStatement("select * from TestValues where id = #{id}");
		List<TableRow> results = dataset.getResultList();

		assertNotNull(results);
		assertEquals(0, results.size());
	}

	public void testSetParameters() {
		dataset.setMaxRows(10);
		dataset.setSelectStatement("select * from TestValues where id = :id");
		dataset.addParameter("id", 4);
		List<TableRow> results = dataset.getResultList();		

		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(4, results.get(0).getValue("ID"));
		assertEquals(4, results.get(0).getValue("VALUE"));
	}

	public void testParamResolver() {
		dataset.addParameterResolver(new ParameterResolver() {

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
		dataset.setSelectStatement("select * from TestValues where id = #{myId}");
		List<TableRow> results = dataset.getResultList();

		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(27, results.get(0).getValue("ID"));
	}

	public void testParamResolverMissingValue() {
		dataset.addParameterResolver(new ParameterResolver() {

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
		dataset
				.setSelectStatement("select * from TestValues where id = #{myId_unknown}");
		List<TableRow> results = dataset.getResultList();

		assertNotNull(results);
		assertEquals(0, results.size());
	}

	public void testInvalidColumnName() {
		List<TableRow> results = dataset.getResultList();
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
		dataset.setSelectStatement("select * from TestValues where id < 21");
		dataset.setCountStatement("select count(1) from TestValues where id < 21");

		assertEquals(21, dataset.getResultCount().intValue());

		assertEquals(21, dataset.getResultList().size());

		dataset.setMaxRows(10);
		assertEquals(false, dataset.isPreviousAvailable());
		assertEquals(true, dataset.isNextAvailable());

		assertEquals(3, dataset.getPageCount());

		List<TableRow> results = dataset.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		assertEquals(false, dataset.isPreviousAvailable());
		assertEquals(true, dataset.isNextAvailable());

		dataset.next();
		results = dataset.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		assertEquals(true, dataset.isPreviousAvailable());
		assertEquals(true, dataset.isNextAvailable());

		dataset.next();
		results = dataset.getResultList();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(true, dataset.isPreviousAvailable());
		assertEquals(false, dataset.isNextAvailable());
	}

	public void testPagingPageSizeMinusOne() {
		log.debug("Testing size minus One");
		// test the paging when the
		dataset.setSelectStatement("select * from TestValues where id < 19");
		dataset.setCountStatement("select count(1) from TestValues where id < 19");

		assertEquals(19, dataset.getResultCount().intValue());
		assertEquals(19, dataset.getResultList().size());

		dataset.setMaxRows(10);
		assertEquals(2, dataset.getPageCount());

		List<TableRow> results = dataset.getResultList();
		assertNotNull(results);
		assertEquals(10, results.size());
		assertEquals(false, dataset.isPreviousAvailable());
		assertEquals(true, dataset.isNextAvailable());

		dataset.next();
		results = dataset.getResultList();
		assertNotNull(results);
		assertEquals(9, results.size());
		assertEquals(true, dataset.isPreviousAvailable());
		assertEquals(false, dataset.isNextAvailable());
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
		StatementDataset<TestValue> qry = new StatementDataset<TestValue>(provider);
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
		return dataset;
	}
}
