/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider.seam;

import javax.persistence.EntityManager;

import org.fluttercode.spigot.provider.jpa.JpaDataset;
import org.fluttercode.spigot.provider.jpa.JpaQueryProvider;
import org.fluttercode.spigot.provider.seam.util.SeamJpaDatasetAdapter;
import org.jboss.seam.annotations.In;

/**
 * Dataset for use in the Seam environment annotated for Seam Managed
 * Persistence Contexts, and resolving parameters using EL expressions. This
 * type of dataset uses a straight Ejbql based JPA query.
 * <p/>
 * You can specify a parameter as having an EL expression and it will resolve it
 * using Seam automatically. i.e.
 * 
 * <pre>
 * p.id = #{personSearchCriteria.id}
 * p.firstName = #{personSearchCriteria.firstName.concat('%')}
 * </pre>
 * 
 * This class can be thought of as a functional equivalent of an EntityQuery but
 * you cannot substitute one for another. You could instead use a
 * {@link SeamJpaDatasetAdapter} as an intermediate alternative.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset returns
 * 
 * @see SeamJpaNativeDataset
 */
public class SeamJpaDataset<T> extends JpaDataset<T> {

	private static final long serialVersionUID = 1L;

	protected SeamJpaDataset(JpaQueryProvider<T> jpaProvider) {
		super(jpaProvider);
		getProvider().addParameterResolver(new SeamParameterResolver());
	}

	public SeamJpaDataset() {
		this(new JpaQueryProvider<T>());
	}

	@In
	public void setEntityManager(EntityManager entityManager) {
		getProvider().setEntityManager(entityManager);
	}
}
