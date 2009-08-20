package org.jdataset;

/**
 * Simple interface for defining a parameter parser that can take a line of a
 * query language and extract the parameters as an array of String. Any
 * implementations should be aware of the different types of parameter syntax
 * that might be used.
 * 
 * @author Andy Gibson
 * 
 */
public interface ParameterParser {

	String[] extractParameters(String line);

}
