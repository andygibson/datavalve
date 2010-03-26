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

package org.fluttercode.spigot;

import java.util.List;

/**
 * Interface for accessing data based on the current state of the provider and
 * {@link Paginator}. Both the result count and data fetch are based on the
 * current state of the provider indicating what data the user wants. This is
 * particularly important in subclasses which may have SQL restrictions.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object returned from this dataset.
 */
public interface DataProvider<T> {

	/**
	 * Returns the number of rows that are in the complete dataset once the
	 * state of the provider has been taken into account.
	 * 
	 * @return The number of rows in the whole dataset
	 */
	Integer fetchResultCount();

	/**
	 * Fetches the (sub)set of data based on the current provider state and the
	 * contents of the Paginator.
	 * 
	 * @param paginator
	 *            Indicates the set of data to return from the complete set.
	 * @return List of objects of type <T> for the rows of the dataset as
	 *         defined by the Paginator.
	 */
	List<T> fetchResults(Paginator paginator);

}
