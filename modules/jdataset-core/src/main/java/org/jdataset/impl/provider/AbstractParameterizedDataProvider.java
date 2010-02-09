package org.jdataset.impl.provider;

import org.jdataset.ParameterManager;
import org.jdataset.impl.DefaultParameterManager;
import org.jdataset.impl.params.ParameterParser;
import org.jdataset.impl.params.RegexParameterParser;
import org.jdataset.provider.ParameterizedDataProvider;

/**
 * Extends the {@link AbstractDataset} to implement the
 * {@link ParameterizedDataProvider} methods. This class adds handling for parameter
 * resolvers, holding a fixed parameter map, extracting parameters from text and
 * resolving parameters.
 * <p>
 * By default, when the dataset is asked to resolve a comma prefixed parameter,
 * it should look in the parameters map first to see if it exists there.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public abstract class AbstractParameterizedDataProvider<T> extends
		AbstractDataProvider<T> implements ParameterizedDataProvider<T> {

	private static final long serialVersionUID = 1L;

	private ParameterParser parameterParser = new RegexParameterParser();
	
	private ParameterManager parameterHandler = new DefaultParameterManager();
	
	public ParameterParser getParameterParser() {
		return parameterParser;
	}

	public void setParameterParser(ParameterParser parameterParser) {
		this.parameterParser = parameterParser;
	}
	
	public ParameterManager getParameterHandler() {
		return parameterHandler;
	}
	
	public void setParameterHandler(ParameterManager parameterHandler) {
		this.parameterHandler = parameterHandler;
	}

}
