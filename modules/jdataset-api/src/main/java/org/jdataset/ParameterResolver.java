package org.jdataset;

import java.io.Serializable;

/**
 * Class that can be used to define components that are used to provide
 * parameter values in response to parameters in {@link QueryDataset}
 * restrictions.
 * 
 * @author Andy Gibson
 * 
 */
public abstract class ParameterResolver implements Serializable {

	public abstract boolean resolveParameter(Parameter parameter);
	public abstract boolean acceptParameter(String parameter);

}
