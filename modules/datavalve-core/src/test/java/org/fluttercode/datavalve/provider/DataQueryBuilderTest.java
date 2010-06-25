/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.provider;

import org.fluttercode.datavalve.provider.util.DataQuery;
import org.fluttercode.datavalve.provider.util.DataQueryBuilder;

import junit.framework.TestCase;

/**
 * @author Andy Gibson
 * 
 */
public class DataQueryBuilderTest extends TestCase {

	protected DataQueryBuilder builder;
	protected DummyDataProvider<Object> provider;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		builder = new DataQueryBuilder();
		builder.setBaseStatement("select x from y");
		provider = new DummyDataProvider<Object>();
		builder.setProvider(provider);
	}

	public void testParameterizedStatement() {
		DataQuery query = builder.build();
		assertEquals("select x from y", query.getStatement());
	}

	public void testParameterizedStatementWithWhere() {
		builder.setBaseStatement("select x from y where a = b");
		DataQuery query = builder.build();
		assertEquals("select x from y where a = b", query.getStatement());
	}

	public void testParameterizedStatementWithParam() {
		builder.setBaseStatement("select x from y where a = :VALUE1");
		DataQuery query = builder.build();
		assertEquals("select x from y where a = :param_0", query.getStatement());
		assertEquals(1, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertFalse(query.getParameters().get(0).isNull());
		assertEquals(new Long(1), query.getParameters().get(0).getValue());

	}

	public void testQueryBuilderWithRestriction() {
		provider.addRestriction("a=b");
		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=b", query.getStatement());
		assertEquals(0, query.getParameters().size());

	}

	public void testQueryBuilderWithParameterizedRestriction() {
		provider.addRestriction("a=b");
		provider.addRestriction("v=:VALUE1");

		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=b AND v=:param_0", query
				.getStatement());
		assertEquals(1, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals(1l, query.getParameters().get(0).getValue());

	}

	public void testQueryBuilderWithParameterizedNullRestriction() {

		provider.addRestriction("a=b");
		provider.addRestriction("v=:NULL");

		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=b", query.getStatement());
		assertEquals(0, query.getParameters().size());
	}

	public void testQueryBuilderWithParameterizedAllowedNullRestriction() {		
		provider.addRestriction("a=b");
		provider.addRestriction("v=:NULL");		
		builder.setAllowNullParameters(true);
		DataQuery query = builder.build();

		assertEquals("select x from y WHERE a=b AND v=:param_0", query
				.getStatement());
		assertEquals(1, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertNull(query.getParameters().get(0).getValue());
	}

	public void testStatementWithNullParameter() {
		// check we allow null parameters in the base statement						
		builder.setAllowNullParameters(true);
		builder.setBaseStatement("select x from y where d = :NULL");

		DataQuery query = builder.build();
		assertEquals("select x from y where d = :param_0", query.getStatement());
		assertEquals(1, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertNull(query.getParameters().get(0).getValue());
	}

	public void testDuplicateParameter() {
		builder
				.setBaseStatement("select x from y where d = :VALUE1 and e = :VALUE1");

		DataQuery query = builder.build();
		
		assertEquals("select x from y where d = :param_0 and e = :param_1",
				query.getStatement());
		assertEquals(2, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals(1l, query.getParameters().get(0).getValue());
		assertEquals("param_1", query.getParameters().get(1).getName());
		assertEquals(1l, query.getParameters().get(1).getValue());

	}

	public void testDuplicateParameterInRestrictions() {
		builder.setBaseStatement("select x from y");
		provider.addRestriction("a=:VALUE1");
		provider.addRestriction("b=:VALUE1");

		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=:param_0 AND b=:param_1", query
				.getStatement());
		assertEquals(2, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals(1l, query.getParameters().get(0).getValue());
		assertEquals("param_1", query.getParameters().get(1).getName());
		assertEquals(1l, query.getParameters().get(1).getValue());

	}

	public void testElParameter() {				
		builder.setBaseStatement("select x from y");
		provider.addRestriction("a=#{id}");

		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=:param_0", query.getStatement());
		assertEquals(1, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals(99, query.getParameters().get(0).getValue());
	}

	public void testDuplicateElParameter() {
		builder.setBaseStatement("select x from y");
		provider.addRestriction("a=#{id}");
		provider.addRestriction("b=#{id}");

		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=:param_0 AND b=:param_1", query
				.getStatement());
		assertEquals(2, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals(99, query.getParameters().get(0).getValue());
		assertEquals("param_1", query.getParameters().get(1).getName());
		assertEquals(99, query.getParameters().get(1).getValue());
	}

	public void testOrderingDefaultAsc() {
		builder.setBaseStatement("select x from y");
		provider.addRestriction("a=#{id}");
		provider.addRestriction("b=#{id}");
		builder.setOrderBy("y.a,y.b");
		DataQuery query = builder.build();
		assertEquals(
				"select x from y WHERE a=:param_0 AND b=:param_1 ORDER BY y.a ASC, y.b ASC",
				query.getStatement());
	}

	public void testOrderingDescending() {
		builder.setBaseStatement("select x from y");
		builder.setOrderAscending(false);
		provider.addRestriction("a=#{id}");
		provider.addRestriction("b=#{id}");
		builder.setOrderBy("y.a,y.b");
		DataQuery query = builder.build();
		assertEquals(
				"select x from y WHERE a=:param_0 AND b=:param_1 ORDER BY y.a DESC, y.b DESC",
				query.getStatement());
	}

	public void testSelectSubQUery() {
		builder
				.setBaseStatement("select x,(select count from o where o.id = :VALUE1 and o.y = y.id) from y");
		provider.addRestriction("a=:VALUE2");
		provider.addRestriction("b=#{id}");
		DataQuery query = builder.build();
		assertEquals(
				"select x,(select count from o where o.id = :param_0 and o.y = y.id) from y WHERE a=:param_1 AND b=:param_2",
				query.getStatement());

		assertEquals(3, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals("param_1", query.getParameters().get(1).getName());
		assertEquals("param_2", query.getParameters().get(2).getName());
		assertEquals(1l, query.getParameters().get(0).getValue());
		assertEquals(2l, query.getParameters().get(1).getValue());
		assertEquals(99, query.getParameters().get(2).getValue());

	}

	public void testSelectSubQUeryNullParam() {
		builder
				.setBaseStatement("select x,(select count from o where o.id = :NULL and o.y = y.id) from y");
		provider.addRestriction("a=:VALUE2");
		provider.addRestriction("b=#{id}");
		DataQuery query = builder.build();
		assertEquals(
				"select x,(select count from o where o.id = :param_0 and o.y = y.id) from y WHERE a=:param_1 AND b=:param_2",
				query.getStatement());

		assertEquals(3, query.getParameters().size());
		assertEquals("param_0", query.getParameters().get(0).getName());
		assertEquals("param_1", query.getParameters().get(1).getName());
		assertEquals("param_2", query.getParameters().get(2).getName());
		assertNull(query.getParameters().get(0).getValue());
		assertEquals(2l, query.getParameters().get(1).getValue());
		assertEquals(99, query.getParameters().get(2).getValue());

	}

	public void testManuallyJoinedRestrictions() {
		builder.setBaseStatement("select x from y");
		provider.addRestriction("a=:VALUE2");
		provider.addRestriction("OR b=:VALUE1");
		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=:param_0 OR b=:param_1", query
				.getStatement());
	}

	public void testManuallyAddedParameters() {
		builder.setBaseStatement("select x from y");
		provider.addRestriction("a=:VALUE2");
		provider.addRestriction("OR b=:VALUE1");
		DataQuery query = builder.build();
		assertEquals("select x from y WHERE a=:param_0 OR b=:param_1", query
				.getStatement());
	}

}
