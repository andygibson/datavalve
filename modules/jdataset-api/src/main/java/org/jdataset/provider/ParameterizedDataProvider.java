package org.jdataset.provider;

import org.jdataset.DataProvider;
import org.jdataset.ParameterManager;
import org.jdataset.ParameterResolver;

/**
 * Extends the {@link ObjectDataset} interface to include parameterization of
 * the dataset. This introduces methods that allows you to define parameters and
 * also attach {@link ParameterResolver} instances to extend the parameter
 * resolution beyond key-value pairs.
 * 
 * @author Andy Gibson
 * 
 * @param <T> The type of objects returned from this query
 */
public interface ParameterizedDataProvider<T> extends DataProvider<T> {

	ParameterManager getParameterHandler();
}
