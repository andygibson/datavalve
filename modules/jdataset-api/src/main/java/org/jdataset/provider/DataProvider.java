package org.jdataset.provider;

import java.util.List;

import org.jdataset.ObjectDataset;
import org.jdataset.Paginator;

/**
 * Base interface for the {@link DataProvider} interfaces which define how to
 * access the data and provide data to the {@link ObjectDataset} instances.
 * <p>
 * The {@link DataProvider} also lets you access the data artbitrarily by
 * calling the {@link DataProvider#fetchResults(Paginator)} method and passing
 * in your own {@link Paginator} interface. This lets us decouple the state
 * from the data. We can hold the pagination information separately from the
 * data it works on.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface DataProvider<T> {

	Integer fetchResultCount();

	List<T> fetchResults(Paginator paginator);

}
