package org.jdataset;

public interface StatementManager {

	public String getCountStatement();

	public void setCountStatement(String countStatement);

	public String getSelectStatement();

	public void setSelectStatement(String selectStatement);
	
	public void init(Class<? extends Object> clazz,String prefix);

}
