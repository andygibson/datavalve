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

package org.fluttercode.datavalve;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.datavalve.params.AbstractParameterResolver;
import org.fluttercode.datavalve.params.Parameter;
import org.fluttercode.datavalve.provider.ParameterizedDataProvider;

/**
 * A singleton instance that provides a place to hold global configuration
 * information. In particular this class holds a list of
 * {@link ParameterResolver} instances and can be used as a global parameter
 * resolver to allow you to specify application wide parameters once.
 * 
 * You can also define common resolvers here that are shared among all the data
 * provider instances, for example, adding an EL parameter resolver.
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
			ParameterizedDataProvider<? extends Object> dataset,
			Parameter parameter) {
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
