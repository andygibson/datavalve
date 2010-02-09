package org.jdataset.impl.provider.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jdataset.Paginator;
import org.jdataset.impl.provider.AbstractQueryDataProvider;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Hibernate based {@link QueryDataProvider}.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public class HibernateDataProvider<T> extends AbstractQueryDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private Session session;

	private final static Logger log = LoggerFactory
			.getLogger(HibernateDataProvider.class);

	/**
	 * 
	 * Builds a Hibernate query from the select statement (including the order
	 * clause) and sets up the maxResults and firstResult properties on the
	 * query and returns the results.
	 * 
	 * @see org.jdataset.impl.provider.AbstractQueryDataProvider#fetchResultsFromDatabase(java.lang.Integer)
	 */
	@Override
	protected final List<T> fetchResultsFromDatabase(Paginator paginator,
			Integer count) {		
		Query qry = buildQuery(getStatementHandler().getSelectStatement(), true, paginator);
		if (count != 0) {
			qry.setMaxResults(count);
		}

		qry.setFirstResult(paginator.getFirstResult());
		@SuppressWarnings("unchecked")
		List<T> results = qry.list();
		return results;
	}

	/**
	 * Builds a Hibernate Query based on the selectStatement parameter and
	 * builds the list of parameters that go with the query. Once the statement
	 * is built, the parameters are iterated through and assigned values on the
	 * query. Finally, the query is returned to the caller. This method is used
	 * for both select and count queries so we cannot handle the
	 * {@link Query#setFirstResult(int)}, {@link Query#setMaxResults(int)}
	 * method calls here. This needs to be done by the caller.
	 * 
	 * @param selectStatement
	 *            The select statement for this query
	 * @param includeOrderBy
	 * @return
	 */
	protected final Query buildQuery(String selectStatement,
			boolean includeOrderBy, Paginator paginator) {

		Map<String, Object> queryParams = new HashMap<String, Object>();

		// build the ejbql statement and parameter list
		String statement = buildStatement(selectStatement, queryParams,
				includeOrderBy, paginator);
		log.debug("Built statement : {}", statement);

		Query qry = getSession().createQuery(statement);

		// set the parameters from the internal parameter list
		for (Entry<String, Object> entry : queryParams.entrySet()) {
			log.debug("Setting query parameter '{}' to value '{}'", entry
					.getKey(), entry.getValue());
			qry.setParameter(entry.getKey(), entry.getValue());
		}

		// return it for use
		return qry;
	}

	public final Integer fetchResultCount() {
		Query qry = buildQuery(getStatementHandler().getCountStatement(), false, null);
		Long result = (Long) qry.uniqueResult();
		return result.intValue();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
