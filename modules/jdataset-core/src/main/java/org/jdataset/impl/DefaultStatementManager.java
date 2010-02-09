package org.jdataset.impl;

import java.io.Serializable;

import org.jdataset.StatementManager;

public class DefaultStatementManager implements StatementManager, Serializable {

	private String selectStatement;
	private String countStatement;

	public String getCountStatement() {
		return countStatement;
	}

	public String getSelectStatement() {
		return selectStatement;
	}

	public void init(Class<? extends Object> clazz, String prefix) {
		setCountStatement(String.format("select count(%s) from %s %s ",prefix,clazz.getSimpleName(),prefix));
		setSelectStatement(String.format("select %s from %s %s ",prefix,clazz.getSimpleName(),prefix));		
	}

	public void setCountStatement(String countStatement) {
		this.countStatement = countStatement;
	}

	public void setSelectStatement(String selectStatement) {
		this.selectStatement = selectStatement;
	}
}
