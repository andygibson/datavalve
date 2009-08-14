package org.jdataset;

import java.io.Serializable;

/**
 * Abstract class that implements {@link ParameterResolver} and
 * {@link Serializable} so we can extend this for anonymous classes without
 * worrying about serialization issues.
 * 
 * @author Andy Gibson
 * 
 */
public abstract class AbstractParameterResolver implements ParameterResolver,
		Serializable {

}
