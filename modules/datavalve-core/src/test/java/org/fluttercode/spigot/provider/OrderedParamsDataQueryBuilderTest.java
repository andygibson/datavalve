/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider;

import org.fluttercode.spigot.provider.util.DataQuery;
import org.fluttercode.spigot.provider.util.DataQueryBuilder;

import junit.framework.TestCase;

/**
 * @author Andy Gibson
 * 
 */
public class OrderedParamsDataQueryBuilderTest extends TestCase {

	protected DataQueryBuilder builder;
	protected DummyDataProvider<Object> provider;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		builder = new DataQueryBuilder();
		builder.setBaseStatement("select x from y");
		provider = new DummyDataProvider<Object>();
		builder.setProvider(provider);
		builder.setOrderedParams(true);
	}

	
	public void testStatementBasics() {
		DataQuery query = builder.build();
		assertEquals("select x from y", query.getStatement());
		assertEquals(0, query.getParameters().size());		
	}
	
	public void testParameterizedStatement() {
		builder.setBaseStatement("select x from y where a = :VALUE1");
		DataQuery query = builder.build();
		
		assertEquals("select x from y where a = ?", query.getStatement());
		assertEquals(1, query.getParameters().size());
		assertEquals(1l, query.getParameters().get(0).getValue());
		assertEquals("?", query.getParameters().get(0).getName());
	}
	public void testNullParameterInStatement() {
		builder.setBaseStatement("select x from y where a = :NULL");
		DataQuery query = builder.build();
		
		assertEquals("select x from y where a = ?", query.getStatement());
		assertEquals(1, query.getParameters().size());		
		assertNull(query.getParameters().get(0).getValue());
		assertEquals("?", query.getParameters().get(0).getName());
		
	}
	public void testParameterizedStatement2Params() {
		builder.setBaseStatement("select x from y where a = :VALUE1 AND b=:VALUE2");
		DataQuery query = builder.build();
		
		assertEquals("select x from y where a = ? AND b=?", query.getStatement());
		assertEquals(2, query.getParameters().size());
		assertEquals(1l, query.getParameters().get(0).getValue());
		assertEquals("?", query.getParameters().get(0).getName());
		assertEquals(2l, query.getParameters().get(1).getValue());
		assertEquals("?", query.getParameters().get(1).getName());		
	}
	
	public void testManualParameterAdding() {
		provider.addRestriction("a=3");
		provider.addRestriction("b=:param",4l);
		provider.addRestriction("c=#{id}");
		provider.addRestriction("d=:VALUE2");
		provider.addRestriction("e=:NULL");//won't get added		
		DataQuery query = builder.build();
		
		
		assertEquals("select x from y WHERE a=3 AND b=? AND c=? AND d=?", query.getStatement());
		assertEquals(3, query.getParameters().size());
		
		//b 
		assertEquals(4l, query.getParameters().get(0).getValue());
		assertEquals("?", query.getParameters().get(0).getName());
		
		//c
		assertEquals(99, query.getParameters().get(1).getValue());
		assertEquals("?", query.getParameters().get(1).getName());

		//d
		assertEquals(2l, query.getParameters().get(2).getValue());
		assertEquals("?", query.getParameters().get(2).getName());

	}
	


}
