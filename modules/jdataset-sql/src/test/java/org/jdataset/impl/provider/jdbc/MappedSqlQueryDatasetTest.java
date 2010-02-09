package org.jdataset.impl.provider.jdbc;

import org.jdataset.dataset.DefaultQueryDataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.provider.QueryDataProvider;

public class MappedSqlQueryDatasetTest extends BaseJdbcDatasetTest<TableRow> {
	
	private static final long serialVersionUID = 1L;
	
	public ObjectDataset<TableRow> buildDataset() {
		QueryDataProvider<TableRow> provider = new MappedJdbcQueryDataset(getConnection());
		provider.getStatementHandler().setSelectStatement("select * from TestValues");
		provider.getStatementHandler().setCountStatement("select count(1) from TestValues");
		provider.getOrderHandler().add("id", "id");
		provider.getOrderHandler().add("value", "value");		
		return new DefaultQueryDataset<TableRow>(provider);		
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
