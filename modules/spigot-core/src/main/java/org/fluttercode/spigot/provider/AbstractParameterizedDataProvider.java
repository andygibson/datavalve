/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.spigot.DatasetEnvironment;
import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.params.ParameterParser;
import org.fluttercode.spigot.params.RegexParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends the {@link AbstractDataProvider} to implement the
 * {@link ParameterizedDataProvider} methods. This class adds handling for
 * parameter resolvers, holding a fixed parameter map, extracting parameters
 * from text and resolving parameters.
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
	private static Logger log = LoggerFactory
			.getLogger(AbstractParameterizedDataProvider.class);

	private ParameterParser parameterParser = new RegexParameterParser();
	private Map<String, Object> parameters = new HashMap<String, Object>();
	private List<ParameterResolver> parameterResolvers = new ArrayList<ParameterResolver>();

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

		Parameter param = new Parameter(name);

		// try and resolve through the global parameter resolvers
		if (DatasetEnvironment.getInstance().resolveParameter(this, param)) {
			return param.getValue();
		}

		for (ParameterResolver resolver : parameterResolvers) {

			if (resolver.acceptParameter(name)) {
				if (resolver.resolveParameter(this, param)) {
					log.debug("Resolved using resolver : '{}'", resolver);
					log.debug("Resolved value as : '{}'", param.getValue());
					return param.getValue();
				}
			}
		}
		return null;
	}

	public ParameterParser getParameterParser() {
		return parameterParser;
	}

	public void setParameterParser(ParameterParser parameterParser) {
		this.parameterParser = parameterParser;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public List<ParameterResolver> getParameterResolvers() {
		return parameterResolvers;
	}

	public void setParameterResolvers(List<ParameterResolver> parameterResolvers) {
		this.parameterResolvers = parameterResolvers;
	}
}
