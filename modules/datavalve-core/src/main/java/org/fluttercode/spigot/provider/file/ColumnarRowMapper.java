package org.fluttercode.spigot.provider.file;

public interface ColumnarRowMapper<T> {
	
	public T mapRow(String[] columns);
	
}
