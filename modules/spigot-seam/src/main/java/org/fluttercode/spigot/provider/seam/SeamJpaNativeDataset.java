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

package org.fluttercode.spigot.provider.seam;

import javax.persistence.EntityManager;

import org.fluttercode.spigot.provider.jpa.JpaDataset;
import org.fluttercode.spigot.provider.jpa.JpaNativeProvider;
import org.fluttercode.spigot.provider.seam.util.SeamJpaNativeDatasetAdapter;
import org.jboss.seam.annotations.In;

/**
 * Dataset for use in the Seam environment annotated for Seam Managed
 * Persistence Contexts, and resolving parameters using EL expressions. This
 * type of dataset uses a Native SQL based JPA query.
 * 
 * You can specify a parameter as having an EL expression and it will resolve it
 * using Seam automatically. i.e.
 * 
 * <pre>
 * p.id = #{personSearchCriteria.id}
 * p.firstName = #{personSearchCriteria.firstName.concat('%')}
 * </pre>
 * 
 * This class can be thought of as a functional equivalent of an EntityQuery
 * except using a native query, but you cannot substitute one for another. You
 * could instead use a {@link SeamJpaNativeDatasetAdapter} as an intermediate
 * alternative.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset returns
 * 
 * @see SeamJpaNativeDataset
 */
// TODO is this right that we subclass query dataset?
public class SeamJpaNativeDataset<T> extends JpaDataset<T> {

	private static final long serialVersionUID = 1L;
	

	protected SeamJpaNativeDataset(JpaNativeProvider<T> jpaProvider) {
		super(jpaProvider);		
		getProvider().addParameterResolver(new SeamParameterResolver());
	}

	public SeamJpaNativeDataset() {
		this(new JpaNativeProvider<T>());		
	}

	@In
	public void setEntityManager(EntityManager entityManager) {
		getProvider().setEntityManager(entityManager);
	}
	
	
}