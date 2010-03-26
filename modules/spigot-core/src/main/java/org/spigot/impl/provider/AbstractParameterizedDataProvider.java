/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.jdataset.impl.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdataset.DatasetEnvironment;
import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.jdataset.impl.params.ParameterParser;
import org.jdataset.impl.params.RegexParameterParser;
import org.jdataset.provider.ParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		//try and resolve through the global parameter resolvers
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
}
