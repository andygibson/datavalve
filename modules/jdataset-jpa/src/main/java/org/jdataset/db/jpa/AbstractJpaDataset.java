package org.jdataset.db.jpa;

import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jdataset.db.AbstractQueryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJpaDataset<T> extends AbstractQueryDataset<T> {

	private static final long serialVersionUID = 1L;
	
	private EntityManager entityManager;
	private static Logger log = LoggerFactory.getLogger(AbstractJpaDataset.class);

	@Override
	protected List<T> fetchResultsFromDatabase(Integer count) {
		Query qry = buildQuery(getSelectStatement());
		if (count != 0) {
			qry.setMaxResults(count);
		}
		
		qry.setFirstResult(getFirstResult());
		@SuppressWarnings("unchecked")
		List<T> results = qry.getResultList();
		return results;
	}

	protected Query buildQuery(String selectStatement) {
		// build the ejbql statement and parameter list
		String ql = buildStatement(selectStatement, getQueryParameters());
		log.debug("Built statement : {}",ql);
		
		// create the actual query (native or JPA)
		Query qry = createJpaQuery(ql);		

		// set the parameters from the internal parameter list
		for (Entry<String, Object> entry : getQueryParameters().entrySet()) {
			log.debug("Setting query parameter '{}' to value '{}'",entry.getKey(),entry.getValue());
			qry.setParameter(entry.getKey(), entry.getValue());
		}

		// return it for use
		return qry;
	}

	/**
	 * Override in Jpa and JpaNative dataset to return the correct type of query
	 * object.
	 * 
	 * @param ql
	 *            Statement the query must execute
	 * @return Query object created from the entity manager and configured with
	 *         the passed in sql.
	 */
	protected abstract Query createJpaQuery(String ql);

	@Override
	protected Integer fetchResultCount() {
		Query qry = buildQuery(getCountStatement());
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
