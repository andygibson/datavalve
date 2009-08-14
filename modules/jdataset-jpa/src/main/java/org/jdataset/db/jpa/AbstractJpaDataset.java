package org.jdataset.db.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.cfg.CreateKeySecondPass;
import org.jdataset.QueryDataset;
import org.jdataset.db.AbstractQueryDataset;
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

	@Override
	protected final List<T> fetchResultsFromDatabase(Integer count) {
		Query qry = buildQuery(getSelectStatement(), true);
		if (count != 0) {
			qry.setMaxResults(count);
		}

		qry.setFirstResult(getFirstResult());
		@SuppressWarnings("unchecked")
		List<T> results = qry.getResultList();
		return results;
	}

	protected final Query buildQuery(String selectStatement,
			boolean includeOrderBy) {

		Map<String, Object> queryParams = new HashMap<String, Object>();

		// build the ejbql statement and parameter list
		String statement = buildStatement(selectStatement, queryParams,
				includeOrderBy);
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
		Query qry = buildQuery(getCountStatement(), false);
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
