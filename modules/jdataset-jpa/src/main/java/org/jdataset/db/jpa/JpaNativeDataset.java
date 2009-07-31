package org.jdataset.db.jpa;

import javax.persistence.Query;

public class JpaNativeDataset<T> extends AbstractJpaDataset<T> {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Query createJpaQuery(String ql) {		
		return getEntityManager().createNativeQuery(ql);
	}

}

