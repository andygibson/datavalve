package org.jdataset;

import java.util.Map;

public interface ParameterManager {

	/**
	 * Returns a map containing a copy of the local parameters that are by
	 * default used in parameter resolution.
	 * 
	 * @return The map holding local parameters
	 */
	public abstract Map<String, Object> getParameters();

	/**
	 * Assign a new map to the local parameter values for this dataset. The map
	 * is copied and used for local parameter resolution
	 * 
	 * @param parameters
	 *            The parameter map to assign to this dataset
	 */
	public abstract void setParameters(Map<String, Object> parameters);

	/**
	 * Helper method to add a new parameter value to the dataset.
	 * 
	 * @param name
	 *            Name of new parameter
	 * @param value
	 *            Value of new parameter
	 */
	public abstract void addParameter(String name, Object value);

	/**
	 * Adds a new parameter resolver to the dataset. This resolver will be
	 * called if the resolvers preceeding it are unable to resolve the
	 * expression.
	 * 
	 * @param resolver
	 *            the new {@link ParameterResolver} to add to this dataset.
	 */
	public void addParameterResolver(ParameterResolver resolver);

	/**
	 * Resolves the parameter defined by <code>name</code>. The name may include
	 * various decorations to indicate what type of expression it is (i.e.
	 * #{name} for EL expressions. This method should use the
	 * {@link ParameterResolver} instances attached to this dataset to resolve
	 * the parameter name to a value. This method should return the first
	 * non-null value that is return from an attached resolver.
	 * 
	 * @param name
	 *            of the parameter to resolve
	 * @return the value of this parameter (null if not found)
	 */
	public Object resolveParameter(String name);

}
