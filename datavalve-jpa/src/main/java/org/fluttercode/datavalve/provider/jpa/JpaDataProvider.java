/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.provider.jpa;

import javax.persistence.EntityManager;

import org.fluttercode.datavalve.provider.QueryDataProvider;

/**
 * Interface for data providers that use JPA so you can access the
 * {@link EntityManager} attribute from an interface based reference.
 * 
 * @author Andy Gibson
 * 
 */
public interface JpaDataProvider<T> extends QueryDataProvider<T> {

	EntityManager getEntityManager();

	void setEntityManager(EntityManager entityManager);
}
