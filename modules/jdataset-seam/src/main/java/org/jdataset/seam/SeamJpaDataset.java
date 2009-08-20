package org.jdataset.seam;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jdataset.db.jpa.JpaDataset;

/**
 * Dataset for use in the Seam environment annotated for Seam Managed
 * Persistence Contexts, and resolving parameters using EL expressions. This
 * type of dataset uses a straight Ejbql based JPA query.
 * 
 * You can specify a parameter as having an EL expression and it will resolve it using Seam automatically. i.e. 
 * <pre>
 * p.id = #{personSearchCriteria.id}
 * p.firstName = #{personSearchCriteria.firstName.concat('%')}
 * </pre>
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset returns
 * 
 * @see SeamJpaNativeDataset
 */
public class SeamJpaDataset<T> extends JpaDataset<T> {

	public SeamJpaDataset() {
		addParameterResolver(new SeamParameterResolver());
	}

	/**
	 * Overridden method so we can inject the entity manager Seam component
	 * using the {@link In} annotation.
	 * 
	 * @see org.jdataset.db.jpa.AbstractJpaDataset#setEntityManager(javax.persistence.EntityManager)
	 */
	@In
	@Override
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}
