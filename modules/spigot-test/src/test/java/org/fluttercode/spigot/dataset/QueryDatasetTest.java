/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fluttercode.spigot.Paginator;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.dataset.QueryDataset;
import org.fluttercode.spigot.impl.provider.AbstractQueryDataProvider;
import org.fluttercode.spigot.impl.provider.DataQuery;
import org.fluttercode.spigot.provider.QueryDataProvider;
import org.fluttercode.spigot.testing.junit.AbstractObjectDatasetJUnitTest;

/**
 * @author Andy Gibson
 * 
 */
public class QueryDatasetTest extends AbstractObjectDatasetJUnitTest<Integer>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	protected QueryDataset<Integer> buildQuery(final int objectCount) {

		QueryDataProvider<Integer> provider = new AbstractQueryDataProvider<Integer>() {

			private static final long serialVersionUID = 1L;

			/*
			 * @Override public String getSelectStatement() { return
			 * "Select o from Object o"; }
			 */

			@Override
			protected List<Integer> queryForResults(DataQuery query,
					Integer firstResult, Integer count) {
				List<Integer> result = new ArrayList<Integer>();
				
				int index = firstResult == null ? 0 : firstResult.intValue();
				int start = index;
				
				if (count == null) {
					count = Integer.MAX_VALUE;
				}
				
				while (index < fetchResultCount()
						&& index < start + count) {

					result.add(new Integer(index));
					index++;
				}
				return result;
			}

			public Integer fetchResultCount() {
				return objectCount;
			}

			@Override
			protected DataQuery buildDataQuery(String baseStatement,
					boolean includeOrdering, Paginator paginator) {
				return new DataQuery();
			}

			@Override
			protected Integer queryForCount(DataQuery query) {
				return objectCount;
			}

		};

		provider.setSelectStatement("Select o from Object o");
		QueryDataset<Integer> res = new QueryDataset<Integer>(provider);

		res.addRestriction("id = #{id}");
		res.addRestriction("firstName = #{person.firstName}");
		res.getOrderKeyMap().put("id", "o.id");
		res.getOrderKeyMap().put("first", "o.firstName");

		res.addParameterResolver(new TestingParameterResolver());
		return res;
	}

	public void testPaginationWithReads() {
		QueryDataset<Integer> qry = buildQuery(100);
		List<Integer> res = qry.getResultList();
		assertEquals(100, res.size());
		qry.setMaxRows(10);
		res = qry.getResultList();
		assertEquals(10, res.size());
		for (int i = 0; i < 10; i++) {
			assertEquals(i, res.get(i).intValue());
		}

		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.next();
		res = qry.getResultList();
		for (int i = 0; i < 10; i++) {
			assertEquals(i + 10, res.get(i).intValue());
		}
		qry.last();
		res = qry.getResultList();
		for (int i = 0; i < 10; i++) {
			assertEquals(i + 90, res.get(i).intValue());
		}
		assertEquals(false, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.previous();
		res = qry.getResultList();
		for (int i = 0; i < 10; i++) {
			assertEquals(i + 80, res.get(i).intValue());
		}

		assertEquals(true, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.first();
		res = qry.getResultList();
		for (int i = 0; i < 10; i++) {
			assertEquals(i, res.get(i).intValue());
		}
		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.last();
		assertEquals(false, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.first();
		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

	}

	public void testPaginationWithoutReads() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.setMaxRows(10);

		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.next();
		assertEquals(true, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.last();
		assertEquals(false, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.previous();
		assertEquals(true, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

		qry.first();
		assertEquals(true, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.last();
		assertEquals(false, qry.isNextAvailable());
		assertEquals(true, qry.isPreviousAvailable());

	}

	public void testPaginationWithoutPaging() {
		QueryDataset<Integer> qry = buildQuery(100);

		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.next();
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.last();
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.previous();
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.first();
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

		qry.last();
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
		assertEquals(false, qry.isPreviousAvailable());

	}

	public void testChangingPagesize() {
		QueryDataset<Integer> qry = buildQuery(100);
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isPreviousAvailable());
		assertEquals(false, qry.isNextAvailable());

		qry.setMaxRows(10);
		assertEquals(1, qry.getPage());
		assertEquals(false, qry.isPreviousAvailable());
		assertEquals(true, qry.isNextAvailable());

		qry.next();
		assertEquals(2, qry.getPage());
		qry.next();
		assertEquals(3, qry.getPage());
		qry.next();
		assertEquals(4, qry.getPage());
		qry.next(); // first result = 40
		assertEquals(5, qry.getPage());
		qry.setMaxRows(80);
		assertEquals(1, qry.getPage());
		assertEquals(40, qry.getFirstResult());
		assertEquals(true, qry.isPreviousAvailable());
		assertEquals(false, qry.isNextAvailable());
		qry.next();
		assertEquals(1, qry.getPage());
		assertEquals(40, qry.getFirstResult());
		qry.first();
		assertEquals(1, qry.getPage());
		qry.next();
		assertEquals(2, qry.getPage());

	}

	public void testPaginationInitNoReadIsPrev() {
		QueryDataset<Integer> qry = buildQuery(100);
		assertEquals(false, qry.isPreviousAvailable());
	}

	public void testPaginationInitNoReadIsNext() {
		QueryDataset<Integer> qry = buildQuery(100);
		assertEquals(false, qry.isNextAvailable());
	}

	public void testPaginationNoRead_Last_IsPrev() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.last();
		assertEquals(false, qry.isPreviousAvailable());
	}

	public void testPaginationNoRead_Last_IsNext() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.last();
		assertEquals(false, qry.isNextAvailable());
	}

	public void testPaginationPaged_NoReadIsPrev() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.setMaxRows(10);
		assertEquals(false, qry.isPreviousAvailable());
	}

	public void testPaginationPaged_NoReadIsNext() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.setMaxRows(10);
		assertEquals(true, qry.isNextAvailable());
	}

	public void testPaginationPaged_NoRead_Last_IsPrev() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.setMaxRows(10);
		qry.last();
		assertEquals(true, qry.isPreviousAvailable());
	}

	public void testPaginationPaged_NoRead_Last_IsNext() {
		QueryDataset<Integer> qry = buildQuery(100);
		qry.setMaxRows(10);
		qry.last();
		assertEquals(10, qry.getPage());
		assertEquals(false, qry.isNextAvailable());
	}

	@Override
	public ObjectDataset<Integer> buildObjectDataset() {
		return buildQuery(100);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}

}
