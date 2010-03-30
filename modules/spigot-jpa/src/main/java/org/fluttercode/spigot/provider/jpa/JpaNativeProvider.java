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

package org.fluttercode.spigot.provider.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A JPA based dataset that uses a native query language to generate the
 * results.
 * 
 * @author Andy Gibson
 * 
 * @param <T> Type of object this dataset contains
 */
public class JpaNativeProvider<T> extends AbstractJpaDataProvider<T> {

	public JpaNativeProvider() {	
	}
	
	public JpaNativeProvider(EntityManager entityManager) {
		super(entityManager);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected Query createJpaQuery(String ql) {
		return getEntityManager().createNativeQuery(ql, getEntityClass());
	}

}
