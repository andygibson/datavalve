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

import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;
import org.fluttercode.spigot.provider.QueryDataProvider;


/**
 * Class that can be used to define components that are used to provide
 * parameter values in response to parameters in {@link QueryDataProvider}
 * restrictions. These parameter resolvers are added to datasets to resolve
 * parameters prior to query execution. Parameter resolvers can also be added to
 * the global parameter resolver list which means it is automatically applied to
 * each query that runs.
 * 
 * @author Andy Gibson
 * 
 */
public interface ParameterResolver {

	/**
	 * Requests that this parameter resolver try and resolve the parameter
	 * passed in as a parameter. The {@link Parameter} contains the name and any
	 * value found should be put into the value property. The boolean return
	 * value indicates whether the parameter was resolved by this resolver.
	 * 
	 * @param dataset
	 *            The dataset this parameter belongs to
	 * @param parameter
	 *            Parameter to resolve
	 * @return true if this resolver was able to resolve the parameter
	 */
	public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,
			Parameter parameter);

	/**
	 * Determines whether or not this parameter is suitable for resolution by
	 * this resolver based on the name of the parameter. For example an EL
	 * resolver may only want parameters that start with a '#{' and end with a
	 * '}'. Other resolvers only want to try with parameters that start with a
	 * colon.
	 * 
	 * @param name
	 *            name of the parameter we want resolving
	 * @return true if this resolver wants to try and resolve this parameter
	 */
	public boolean acceptParameter(String name);
}
