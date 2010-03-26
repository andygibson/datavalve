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
	boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,
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
	boolean acceptParameter(String name);
}
