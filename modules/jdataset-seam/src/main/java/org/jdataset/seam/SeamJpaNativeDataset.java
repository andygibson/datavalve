package org.jdataset.seam;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jdataset.db.jpa.JpaNativeDataset;

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
