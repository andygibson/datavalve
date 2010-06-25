package org.fluttercode.spigot.dataset;

import org.fluttercode.spigot.provider.file.CommaDelimitedProvider;

/**
 * Dataset for use with {@link CommaDelimitedProvider} provider instances.
 *  
 * @author Andy Gibson
 *
 * @param <T> Type of object returned from this dataset
 */
public class CommaDelimitedDataset<T> extends Dataset<T, CommaDelimitedProvider<T>>{

	private static final long serialVersionUID = 1L;

	public CommaDelimitedDataset() {
		super();
	}

	public CommaDelimitedDataset(CommaDelimitedProvider<T> provider) {
		super(provider);
	}
	
	

}
