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

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.spigot.params.AbstractParameterResolver;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;

/**
 * A singleton instance that provides a place to hold global configuration
 * information. In particular this class holds a list of
 * {@link ParameterResolver} instances and can be used as a global parameter
 * resolver to allow you to specify application wide parameters once.
 * 
 * @author Andy Gibson
 * 
 */
public class DatasetEnvironment implements ParameterResolver {

	private static DatasetEnvironment instance = new DatasetEnvironment();

	/**
	 * Make the constructor private
	 */
	private DatasetEnvironment() {
		addDefaultParameterResolver();
	}

	public static DatasetEnvironment getInstance() {
		return instance;
	}

	private List<ParameterResolver> parameterResolvers = new ArrayList<ParameterResolver>();

	private void addDefaultParameterResolver() {
		addParameterResolver(new AbstractParameterResolver() {

			private static final long serialVersionUID = 1L;
			
			public boolean resolveParameter(
					ParameterizedDataProvider<? extends Object> dataset,
					Parameter parameter) {
				if (dataset == null) {
					throw new IllegalArgumentException(
							"Null dataset in provider parameter resolution, you need to write the param resolver which accepts a data provider as a parameter");
				}
				String strippedName = parameter.getName().substring(1);
				if (dataset.getParameters().containsKey(strippedName)) {
					parameter.setValue(dataset.getParameters()
							.get(strippedName));
					return true;
				}
				return false;
			}

			public boolean acceptParameter(String name) {
				return name.startsWith(":");
			}
		});
	}

	public void addParameterResolver(ParameterResolver parameterResolver) {
		parameterResolvers.add(parameterResolver);
	}

	public boolean resolveParameter(
			ParameterizedDataProvider<? extends Object> dataset, Parameter parameter) {
		for (ParameterResolver resolver : parameterResolvers) {
			if (resolver.acceptParameter(parameter.getName())) {
				if (resolver.resolveParameter(dataset, parameter)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean acceptParameter(String name) {
		// accept all types
		return true;
	}

}
