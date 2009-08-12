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

		log.debug("Attempting to resolve parameter : '{}'", name);
		// try to find locally first
		if (getParameters().containsKey(name)) {
			// return this value since it is contained in the parameters
			// we return this even if it is zero because that is what it is
			// defined as
			Object value = getParameters().get(name);
			log.debug("Resolved parameter as : '{}'", value);
			return value;
		}

		log.debug("Checking parameter resolvers");
		Parameter param = new Parameter(name);
		for (ParameterResolver resolver : parameterResolvers) {
			if (resolver.resolveParameter(param)) {
				log.debug("Resolved using resolver : '{}'", resolver);
				log.debug("Resolved value as : '{}'", param.getValue());
				return param.getValue();
			}
		}
		return null;
	}

	protected String[] extractExpressions(String restriction) {
		if (restriction == null || restriction.length() == 0) {
			return new String[0];

		}
		List<String> list = new ArrayList<String>();

		int startIndex = restriction.indexOf("#{");
		while (startIndex != -1) {
			int endIndex = restriction.indexOf("}", startIndex);
			if (endIndex == -1) {
				throw new RuntimeException(
						"Expression does not have ending '}' in restriction '"
								+ restriction + "'");
			}

			String exp = restriction.substring(startIndex + 2, endIndex);
			list.add(exp);
			startIndex = restriction.indexOf("#{", endIndex);
		}

		String[] results = new String[list.size()];
		return list.toArray(results);
	}

}
