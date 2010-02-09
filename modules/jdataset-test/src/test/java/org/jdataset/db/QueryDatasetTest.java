package org.jdataset.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.dataset.DefaultQueryDataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.dataset.QueryDataset;
import org.jdataset.impl.provider.AbstractQueryDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;

public class QueryDatasetTest extends AbstractObjectDatasetJUnitTest<Integer>
		implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected QueryDataset<Integer> buildQuery(final int objectCount) {
		
		QueryDataProvider<Integer> provider = new AbstractQueryDataProvider<Integer>() {

			private static final long serialVersionUID = 1L;

/*			@Override
			public String getSelectStatement() {
				return "Select o from Object o";
			}*/

			
			public List<Integer> fetchResultsFromDatabase(Paginator paginator,Integer count) {
				List<Integer> result = new ArrayList<Integer>();
				int index = paginator.getFirstResult();
				if (count == 0) {
					count = Integer.MAX_VALUE;
				}
				while (index < fetchResultCount()
						&& index < paginator.getFirstResult() + count) {

					result.add(new Integer(index));
					index++;
				}
				return result;
			}
			
			public Integer fetchResultCount() {
				return objectCount;
			}

		};

		provider.getStatementHandler().setSelectStatement("Select o from Object o");
		QueryDataset<Integer> res = new DefaultQueryDataset<Integer>(provider);
		
		res.getRestrictionHandler().add("id = #{id}");
		res.getRestrictionHandler().add("firstName = #{person.firstName}");
		res.getOrderHandler().add("id", "o.id");
		res.getOrderHandler().add("first", "o.firstName");

		res.getParameterHandler().addParameterResolver(new TestingParameterResolver());
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

	public void testStatementInitializer() {
		QueryDataProvider<Integer>  qry = new AbstractQueryDataProvider<Integer>() {

			@Override
			protected List<Integer> fetchResultsFromDatabase(
					Paginator paginator, Integer count) {
				return null;
			}

			@Override
			public Integer fetchResultCount() {
				return null;
			}
			
		};
		qry.init(QueryDatasetTest.class, "c");
		
		assertEquals("select c from QueryDatasetTest c ", qry.getStatementHandler().getSelectStatement());
		assertEquals("select count(c) from QueryDatasetTest c ", qry.getStatementHandler().getCountStatement());		
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
