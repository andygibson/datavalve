package org.jdataset.sql;

import org.jdataset.ObjectDataset;
import org.jdataset.QueryDataset;

public class MappedSqlQueryDatasetTest extends BaseJdbcDatasetTest<TableRow> {
	
	public ObjectDataset<TableRow> buildDataset() {
		QueryDataset<TableRow> result = new MappedJdbcQueryDataset(getConnection());
		result.setSelectStatement("select * from TestValues");
		result.setCountStatement("select count(1) from TestValues");
		result.getOrderKeyMap().put("id", "id");
		result.getOrderKeyMap().put("value", "value");		
		return result;		
	}
	

	@Override
	public ObjectDataset<TableRow> buildObjectDataset() {
		return buildDataset();
	}

	public void testQueryWithOrdering() {
		ObjectDataset<TableRow> ds= buildDataset();
		ds.setOrderKey("id");		
		assertEquals(getDataRowCount(),ds.getResultCount().intValue());
	}
}
