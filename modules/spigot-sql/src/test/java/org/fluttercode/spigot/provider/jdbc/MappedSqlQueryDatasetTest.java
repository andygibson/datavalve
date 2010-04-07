/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider.jdbc;

import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.dataset.QueryDataset;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.provider.jdbc.MappedJdbcQueryDataset;
import org.fluttercode.spigot.provider.jdbc.TableRow;

/**
 * @author Andy Gibson
 * 
 */
public class MappedSqlQueryDatasetTest extends BaseJdbcDatasetTest<TableRow> {
	
	private static final long serialVersionUID = 1L;
	
	public ObjectDataset<TableRow> buildDataset() {
		QueryDataProvider<TableRow> provider = new MappedJdbcQueryDataset(getConnection());
		provider.setSelectStatement("select * from TestValues");
		provider.setCountStatement("select count(1) from TestValues");
		provider.getOrderKeyMap().put("id", "id");
		provider.getOrderKeyMap().put("value", "value");		
		return new QueryDataset<TableRow>(provider);		
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
