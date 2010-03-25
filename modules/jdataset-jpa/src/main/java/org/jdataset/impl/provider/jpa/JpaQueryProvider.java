/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.jdataset.impl.provider.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * JPA based query that uses the Ejbql syntax for querying.
 *  
 * @author Andy Gibson
 *
 * @param <T>
 */
public class JpaQueryProvider<T> extends AbstractJpaDataProvider<T> {

	public JpaQueryProvider() {
		// TODO Auto-generated constructor stub
	}
	
	public JpaQueryProvider(EntityManager entityManager) {
		super(entityManager);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Query createJpaQuery(String ql) {		
		return getEntityManager().createQuery(ql);
	}
}
