package org.jdataset.impl.provider;

import java.util.ArrayList;
import java.util.List;

import org.jdataset.Parameter;

public class DataQuery {

	private String statement;
	
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	public String getStatement() {
		return statement;
	}
	
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
}
