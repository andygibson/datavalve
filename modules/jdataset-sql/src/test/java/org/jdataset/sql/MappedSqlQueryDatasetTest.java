package org.jdataset.sql;

import org.jdataset.ObjectDataset;
import org.jdataset.QueryDataset;

public class MappedSqlQueryDatasetTest extends BaseSqlDatasetTest<TableRow> {
	
	public ObjectDataset<TableRow> buildDataset() {
		QueryDataset<TableRow> result = new MappedSqlQueryDataset(getConnection());
		result.setSelectStatement("select * from TestValues");
		result.setCountStatement("select count(1) from TestValues");
		return result;		
	}
	

	@Override
	public ObjectDataset<TableRow> buildObjectDataset() {
		return buildDataset();
	}

}
