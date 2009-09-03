package org.jdataset.db.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jdataset.AbstractQueryDataset;
import org.jdataset.Paginator;
import org.jdataset.QueryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for a JPA based {@link QueryDataset}. Override and implement
 * {@link #createJpaQuery(String)} to create a query of the type needed.
 * 
 * @see JpaDataset
 * @see JpaNativeDataset
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public abstract class AbstractJpaDataset<T> extends AbstractQueryDataset<T> {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;
	private final static Logger log = LoggerFactory
			.getLogger(AbstractJpaDataset.class);

	/**
	 * 
	 * Builds a JPA query from the select statement (including the order clause)
	 * and sets up the maxResults and firstResult properties on the query and
	 * returns the results.
	 * 
	 * @see org.jdataset.AbstractQueryDataset#fetchResultsFromDatabase(java.lang.Integer)
	 */
	@Override
	protected final List<T> fetchResultsFromDatabase(Paginator paginator,Integer count) {
		Query qry = buildQuery(getSelectStatement(), true,paginator);
		if (count != 0) {
			qry.setMaxResults(count);
		}

		qry.setFirstResult(paginator.getFirstResult());
		@SuppressWarnings("unchecked")
		List<T> results = qry.getResultList();
		return results;
	}

	/**
	 * Builds a JPA Query based on the selectStatement parameter and builds the
	 * list of parameters that go with the query. Once the statement is built,
	 * the parameters are iterated through and assigned values on the query.
	 * Finally, the query is returned to the caller. This method is used for
	 * both select and count queries so we cannot handle the
	 * {@link Query#setFirstResult(int)}, {@link Query#setMaxResults(int)}
	 * method calls here.
	 * <p>
	 * The call to {@link #buildStatement(String, Map, boolean)} generates the
	 * final query statement and the parameters. {@link #createJpaQuery(String)}
	 * is an abstract method that should be implemented in subclasses. This
	 * method should return an Ejbql or Native style query depending on the
	 * implementation of the subclass.
	 * 
	 * 
	 * @param selectStatement
	 *            The select statement for this query
	 * @param includeOrderBy
	 * @return
	 */
	protected final Query buildQuery(String selectStatement,
			boolean includeOrderBy,Paginator paginator) {

		Map<String, Object> queryParams = new HashMap<String, Object>();

		// build the ejbql statement and parameter list
		String statement = buildStatement(selectStatement, queryParams,
				includeOrderBy,paginator);
		log.debug("Built statement : {}", statement);

		// create the actual query (native or JPA)
		Query qry = createJpaQuery(statement);

		// set the parameters from the internal parameter list
		for (Entry<String, Object> entry : queryParams.entrySet()) {
			log.debug("Setting query parameter '{}' to value '{}'", entry
					.getKey(), entry.getValue());
			qry.setParameter(entry.getKey(), entry.getValue());
		}

		// return it for use
		return qry;
	}

	/**
	 * Override to create the specific type of query to use.
	 * 
	 * @see JpaDataset
	 * @see JpaNativeDataset
	 * 
	 * @param ql
	 *            Statement the query must execute
	 * @return Query object created from the entity manager and configured with
	 *         the passed in sql.
	 */
	protected abstract Query createJpaQuery(String ql);

	@Override
	protected final Integer fetchResultCount() {
		Query qry = buildQuery(getCountStatement(), false,null);
		Long result = (Long) qry.getSingleResult();
		return new Integer(result.intValue());
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
