package org.jdataset.combined;

import org.jdataset.ObjectDataset;
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
public interface StatementDataset<T> extends ObjectDataset<T>,StatementDataProvider<T> {

}
