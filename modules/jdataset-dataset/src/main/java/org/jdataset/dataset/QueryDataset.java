package org.jdataset.dataset;

import org.jdataset.provider.QueryDataProvider;

/**
 * This interface combines the {@link ObjectDataset} interface and the
 * {@link QueryDataProvider} interface so you can reference a single
 * object that implements these two interfaces combined. 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object this dataset returns
 */
//TODO do we even need this? We have the Provider Query Dataset that could be renamed better
public interface QueryDataset<T> extends ObjectDataset<T>,QueryDataProvider<T> {

}
