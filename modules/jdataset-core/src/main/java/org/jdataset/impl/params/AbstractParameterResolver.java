package org.jdataset.impl.params;

import java.io.Serializable;

import org.jdataset.ParameterResolver;

/**
 * Abstract class that implements {@link ParameterResolver} and
 * {@link Serializable} so we can extend this for anonymous classes without
 * worrying about serialization issues. It also implements one or two helper
 * methods that you can re-use to determine if a parameter name is of a certain
 * format.
 * <p>
 * Override this class and implement the two interface methods to provide a
 * custom parameter resolver.
 * 
 * @author Andy Gibson
 * 
 */
public abstract class AbstractParameterResolver implements ParameterResolver,
		Serializable {

	private static final long serialVersionUID = 1L;
	
	public final boolean isElExpression(String name) {
		if (name != null) {
			return name.startsWith("#{") && name.endsWith("}");
		}
		return false;
	}

	public final boolean isCommaExpression(String name) {
		if (name != null) {
			return name.startsWith(":");
		}
		return false;
	}

}
