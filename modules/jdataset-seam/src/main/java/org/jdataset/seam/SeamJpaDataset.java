package org.jdataset.seam;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jdataset.db.jpa.JpaDataset;

public class SeamJpaDataset<T> extends JpaDataset<T> {

	public SeamJpaDataset() {
		addParameterResolver(new SeamParameterResolver());
	}
		
	@In
	@Override
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}
