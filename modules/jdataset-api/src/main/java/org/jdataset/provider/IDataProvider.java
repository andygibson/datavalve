package org.jdataset.provider;

import java.util.List;

import org.jdataset.IObjectDataset;
import org.jdataset.IPaginator;

/**
 * Base interface for the {@link IDataProvider} interfaces which define how to
 * access the data and provide data to the {@link IObjectDataset} instances.
 * <p>
 * The {@link IDataProvider} also lets you access the data artbitrarily by
 * calling the {@link IDataProvider#fetchResults(IPaginator)} method and passing
 * in your own {@link IPaginator} interface. This lets us decouple the state
 * from the data. We can hold the pagination information separately from the
 * data it works on.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface IDataProvider<T> {

	Integer fetchResultCount();

	List<T> fetchResults(IPaginator paginator);

}
