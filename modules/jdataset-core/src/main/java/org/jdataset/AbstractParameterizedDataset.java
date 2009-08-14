package org.jdataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends the {@link AbstractDataset} to implement the
 * {@link ParameterizedDataset} methods. This class adds handling for parameter
 * resolvers, holding a fixed parameter map, resolving parameters, extracting
 * parameters from the text and resolving parameters.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public abstract class AbstractParameterizedDataset<T> extends
		AbstractDataset<T> implements ParameterizedDataset<T> {

	private static final long serialVersionUID = 1L;
	
	private ParameterParser parameterParser = new RegexParameterParser();

	private static Logger log = LoggerFactory
			.getLogger(AbstractParameterizedDataset.class);

	private Map<String, Object> parameters = new HashMap<String, Object>();
	private List<ParameterResolver> parameterResolvers = new ArrayList<ParameterResolver>();

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public void addParameterResolver(ParameterResolver resolver) {
		parameterResolvers.add(resolver);
	}

	public Object resolveParameter(String name) {

		if (name == null) {
			return null;
		}

	/*	log.debug("Attempting to resolve parameter : '{}'", name);
		// try to find locally first
		
			
		if (getParameters().containsKey(name)) {
			// return this value since it is contained in the parameters
			// we return this even if it is zero because that is what it is
			// defined as
			Object value = getParameters().get(name);
			log.debug("Resolved parameter locally as : '{}'", value);
			return value;
		}

		log.debug("Checking parameter resolvers");*/
		Parameter param = new Parameter(name);

		for (ParameterResolver resolver : parameterResolvers) {

			if (resolver.acceptParameter(name)) {
				if (resolver.resolveParameter(param)) {
					log.debug("Resolved using resolver : '{}'", resolver);
					log.debug("Resolved value as : '{}'", param.getValue());
					return param.getValue();
				}
			}
		}
		return null;
	}

	protected String[] extractParameters(String restriction) {
		if (parameterParser == null) {
			throw new IllegalStateException(
					"Parameter parser is null for parameterized dataset");

		}
		return parameterParser.extractParameters(restriction);
	}



	public ParameterParser getParameterParser() {
		return parameterParser;
	}

	public void setParameterParser(ParameterParser parameterParser) {
		this.parameterParser = parameterParser;
	}
	
	public AbstractParameterizedDataset() {
		addParameterResolver(new AbstractParameterResolver() {
			
			public boolean resolveParameter(Parameter parameter) {
				String paramName = parameter.getName().substring(1);				
				if (parameters.containsKey(paramName)) {
					parameter.setValue(parameters.get(paramName));
					return true;
				}
				return false;
			}
			
			public boolean acceptParameter(String parameter) {
				return parameter.startsWith(":");
			}
		});
	}

}
