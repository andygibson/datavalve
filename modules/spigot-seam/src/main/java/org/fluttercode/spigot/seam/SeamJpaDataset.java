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

package org.jdataset.seam;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jdataset.dataset.QueryDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.jdataset.impl.provider.jpa.JpaDataset;
import org.jdataset.impl.provider.jpa.JpaQueryProvider;

/**
 * Dataset for use in the Seam environment annotated for Seam Managed
 * Persistence Contexts, and resolving parameters using EL expressions. This
 * type of dataset uses a straight Ejbql based JPA query.
 * 
 * You can specify a parameter as having an EL expression and it will resolve it
 * using Seam automatically. i.e.
 * 
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
//TODO is this right that we subclass the jpa dataset and not the query dataset?
public class SeamJpaDataset<T> extends JpaDataset<T> {

	private static final long serialVersionUID = 1L;
	
	private final JpaDataProvider<T> jpaProvider;
	
	protected SeamJpaDataset(JpaQueryProvider<T> jpaProvider) {
		super(jpaProvider);
		this.jpaProvider = jpaProvider;		
		jpaProvider.addParameterResolver(new SeamParameterResolver());
	}
	
	
	public SeamJpaDataset() {
		this(new JpaQueryProvider<T>());
	}
	
	@In	
	public void setEntityManager(EntityManager entityManager) {
		jpaProvider.setEntityManager(entityManager);
	}
}
