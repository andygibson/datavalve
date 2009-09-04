package org.jdataset.combo;

import org.jdataset.provider.IStatementDataProvider;

public class StatementDataset<T> extends ParameterizedDataset<T> implements IStatementDataset<T> {

	private static final long serialVersionUID = 1L;

	protected IStatementDataProvider<T> getCastProvider() {
		return (IStatementDataProvider<T>) super.getProvider();
	}
	
	public StatementDataset(IStatementDataProvider<T> provider) {
		super(provider);
	}

	public String getCountStatement() {
		return getCastProvider().getCountStatement();
	}

	public String getSelectStatement() {
		return getCastProvider().getSelectStatement();
	}

	public void setCountStatement(String countStatement) {
		getCastProvider().setCountStatement(countStatement);		
	}

	public void setSelectStatement(String selectStatement) {
		getCastProvider().setSelectStatement(selectStatement);		
	}

}
