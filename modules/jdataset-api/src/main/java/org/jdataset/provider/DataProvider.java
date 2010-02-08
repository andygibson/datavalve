package org.jdataset.provider;

import java.util.List;

import org.jdataset.Paginator;

/**
 * Interface for accessing data based on the current state of the provider and
 * {@link Paginator}. Both the result count and data fetch are based on the
 * current state of the provider indicating what data the user wants. This is
 * particularly important in subclasses which may have SQL restrictions.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object returned from this dataset.
 */
public interface DataProvider<T> {

	/**
	 * Returns the number of rows that are in the complete dataset once the
	 * state of the provider has been taken into account.
	 * 
	 * @return The number of rows in the whole dataset
	 */
	Integer fetchResultCount();

	/**
	 * Fetches the (sub)set of data based on the current provider state and the
	 * contents of the Paginator.
	 * 
	 * @param paginator
	 *            Indicates the set of data to return from the complete set.
	 * @return List of objects of type <T> for the rows of the dataset as
	 *         defined by the Paginator.
	 */
	List<T> fetchResults(Paginator paginator);

}
