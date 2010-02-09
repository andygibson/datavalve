package org.jdataset.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jdataset.DatasetEnvironment;
import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultParameterResolverManager implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(DefaultParameterResolverManager.class);

	private List<ParameterResolver> resolvers = new ArrayList<ParameterResolver>();

	public void add(ParameterResolver resolver) {
		resolvers.add(resolver);
	}

	public Object resolveParameter(String name) {

		if (name == null) {
			return null;
		}

		Parameter param = new Parameter(name);

		// try and resolve through the global parameter resolvers
		if (DatasetEnvironment.getInstance().resolveParameter(null, param)) {
			return param.getValue();
		}

		for (ParameterResolver resolver : resolvers) {

			if (resolver.acceptParameter(name)) {
				if (resolver.resolveParameter(null, param)) {
					log.debug("Resolved using resolver : '{}'", resolver);
					log.debug("Resolved value as : '{}'", param.getValue());
					return param.getValue();
				}
			}
		}
		return null;
	}

	public void remove(ParameterResolver resolver) {
		resolvers.remove(resolver);
	}

}
