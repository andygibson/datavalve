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

package org.fluttercode.datavalve.provider.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.fluttercode.datavalve.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * <p>
 * Base class for testing datasets that use JDBC and SQL to fetch the data. This
 * class inherits from the {@link AbstractObjectDatasetTest} and builds the
 * database structure and provides a jdbc connection for the datasets.
 * </p>
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object returned from the dataset used in the abstract
 *            test cases.
 */
public abstract class BaseJdbcDatasetTest<T> extends AbstractObjectDatasetJUnitTest<T> {

	private static final long serialVersionUID = 1L;
	
	
	public class TestValue {
		int id;
		int value;
	}

	private  transient Connection connection;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection(
					"jdbc:hsqldb:mem:unit-testing-jpa", "sa", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception during HSQL database startup.");
		}
		buildTestData();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// shutdown db
		try {
			getConnection().createStatement().execute("SHUTDOWN");
			getConnection().close();
		} catch (Exception ex) {
		}
	}

	private void buildTestData() {
		String sql = "create table TestValues (Id Integer not null,value Integer,primary Key (Id))";

		try {

			Statement statement = connection.createStatement();

			statement.execute(sql);

			sql = "create table persons (id integer not null, first_name varchar(16), last_name varchar(16), primary key (id))";
			statement.execute(sql);

			sql = String
					.format("insert into Persons (id,first_name,last_name) values (?,?,?)");
			PreparedStatement prepStatement = null;
			prepStatement = getConnection().prepareStatement(sql);

			for (int i = 0; i < 100; i++) {
				prepStatement.setInt(1, i);				
				prepStatement.setString(2, getDataFactory().getFirstName());
				prepStatement.setString(3, getDataFactory().getFirstName());
				prepStatement.execute();
			}

			for (int i = 0; i < 100; i++) {
				sql = String.format(
						"insert into TestValues (id,value) values (%d,%d)", i,
						i);
				statement.execute(sql);
			}

			//
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void testDataset() {
		PreparedStatement s;
		try {
			s = getConnection().prepareStatement(
					"select count(1) from TestValues");
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

}
