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

package org.fluttercode.spigot;

import java.util.List;

import org.fluttercode.spigot.provider.AbstractDataProvider;

/**
 * Interface for accessing data based on the current state of the provider and
 * {@link Paginator}. Both the result count and data fetch are based on the
 * current state of the provider indicating what data the user wants. This is
 * particularly important in subclasses which may have SQL restrictions.
 * <p/>
 * Typically, for implementing new data providers you should subclass the
 * {@link AbstractDataProvider}. This class implements the fetch actions, and
 * provides methods for overriding to alter the behavior in data provider
 * implementations and also in subclasses of those providers for customization
 * at the application development level.
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
	 * <p/>
	 * 
	 * @return The number of rows in the whole dataset
	 */
	public Integer fetchResultCount();

	/**
	 * Fetches the (sub)set of data based on the current provider state and the
	 * contents of the Paginator.
	 * 
	 * @param paginator
	 *            Indicates the set of data to return from the complete set.
	 * @return List of objects of type <T> for the rows of the dataset as
	 *         defined by the Paginator.
	 */
	public List<T> fetchResults(Paginator paginator);

}
