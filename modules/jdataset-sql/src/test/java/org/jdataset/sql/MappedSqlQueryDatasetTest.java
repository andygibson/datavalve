package org.jdataset.sql;

import org.jdataset.IObjectDataset;
import org.jdataset.combo.QueryDataset;
import org.jdataset.provider.IQueryDataProvider;

public class MappedSqlQueryDatasetTest extends BaseJdbcDatasetTest<TableRow> {
	
	private static final long serialVersionUID = 1L;
	
	public IObjectDataset<TableRow> buildDataset() {
		IQueryDataProvider<TableRow> provider = new MappedJdbcQueryDataset(getConnection());
		provider.setSelectStatement("select * from TestValues");
		provider.setCountStatement("select count(1) from TestValues");
		provider.getOrderKeyMap().put("id", "id");
		provider.getOrderKeyMap().put("value", "value");		
		return new QueryDataset<TableRow>(provider);		
	}
	

	@Override
	public IObjectDataset<TableRow> buildObjectDataset() {
		return buildDataset();
	}

	public void testQueryWithOrdering() {
		IObjectDataset<TableRow> ds= buildDataset();
		ds.setOrderKey("id");		
		assertEquals(getDataRowCount(),ds.getResultCount().intValue());
	}
}
