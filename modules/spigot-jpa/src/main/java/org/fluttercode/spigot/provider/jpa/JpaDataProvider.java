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

import org.fluttercode.spigot.provider.QueryDataProvider;

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
