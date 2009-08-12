package org.jdataset;

/**
 * Interface that can be used to define components that are used to provide
 * parameter values in response to parameters in {@link QueryDataset}
 * restrictions.
 * 
 * @author Andy Gibson
 * 
 */
public interface ParameterResolver {

	boolean resolveParameter(Parameter parameter);

}
