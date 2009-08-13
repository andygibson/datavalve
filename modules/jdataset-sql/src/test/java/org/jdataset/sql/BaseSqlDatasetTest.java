package org.jdataset.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jdataset.testing.TestDataFactory;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;

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
public abstract class BaseSqlDatasetTest<T> extends AbstractObjectDatasetJUnitTest<T> {

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
				
				prepStatement.setString(2, TestDataFactory.getFirstName());
				prepStatement.setString(3, TestDataFactory.getFirstName());
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
