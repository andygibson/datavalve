package org.jdataset.impl.provider.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcDataProvider<T> extends AbstractJdbcDataProvider<T> {

	private static final long serialVersionUID = 1L;
	
	private ResultSetObjectMapper<T> resultSetObjectMapper;

	@Override
	public T createObjectFromResultSet(ResultSet resultSet) throws SQLException {
		if (resultSetObjectMapper == null) {
			throw new IllegalStateException("Result set object mapper is null");
		}		
		return resultSetObjectMapper.createObjectFromResultSet(resultSet);
	}
	
	public ResultSetObjectMapper<T> getResultSetObjectMapper() {
		return resultSetObjectMapper;
	}
	
	public void setResultSetObjectMapper(
			ResultSetObjectMapper<T> resultSetObjectMapper) {		
		this.resultSetObjectMapper = resultSetObjectMapper;
	}

}
