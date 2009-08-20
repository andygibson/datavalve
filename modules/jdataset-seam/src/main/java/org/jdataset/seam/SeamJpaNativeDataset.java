package org.jdataset.seam;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jdataset.db.jpa.JpaNativeDataset;


/**
 * Dataset for use in the Seam environment annotated for Seam Managed
 * Persistence Contexts, and resolving parameters using EL expressions. This
 * type of dataset uses a Native SQL based JPA query.
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
public class SeamJpaNativeDataset<T> extends JpaNativeDataset<T> {

	public SeamJpaNativeDataset() {
		addParameterResolver(new SeamParameterResolver());
	}
		
	@In
	@Override
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}
