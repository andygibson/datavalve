package org.jdataset.dataset;

import org.jdataset.provider.StatementDataProvider;

/**
 * This interface combines the {@link ObjectDataset} interface and the
 * {@link StatementDataProvider} interface so you can reference a single
 * object that implements these two interfaces combined. 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object this dataset returns
 */
//TODO do we even need this? We have the Provider Query Dataset that could be renamed better
public interface StatementDataset<T> extends ObjectDataset<T>,StatementDataProvider<T> {

}
