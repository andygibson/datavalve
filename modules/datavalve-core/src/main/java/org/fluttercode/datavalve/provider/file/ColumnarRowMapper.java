package org.fluttercode.datavalve.provider.file;

public interface ColumnarRowMapper<T> {
	
	public T mapRow(String[] columns);
	
}
