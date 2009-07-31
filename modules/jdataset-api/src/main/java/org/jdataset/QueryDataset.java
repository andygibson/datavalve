package org.jdataset;

import java.util.List;
import java.util.Map;

import org.jdataset.ParameterizedDataset;

public interface QueryDataset<T> extends ParameterizedDataset<T> {

	String getSelectStatement();

	void setSelectStatement(String selectStatement);

	String getCountStatement();

	void setCountStatement(String countStatement);

	public abstract Map<String, String> getOrderKeyMap();

	public abstract void setOrderKeyMap(Map<String, String> orderKeyMap);

	public abstract List<String> getRestrictions();

	public abstract void setRestrictions(List<String> restrictions);

	public abstract String getOrderKey();

	public abstract void setOrderKey(String orderKey);	

}
