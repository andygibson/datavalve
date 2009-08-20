package org.jdataset;

import java.util.ArrayList;
import java.util.List;

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

			public boolean resolveParameter(
					ParameterizedDataset<? extends Object> dataset,
					Parameter parameter) {
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
			ParameterizedDataset<? extends Object> dataset, Parameter parameter) {
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
		//accept all types
		return true;
	}

}
