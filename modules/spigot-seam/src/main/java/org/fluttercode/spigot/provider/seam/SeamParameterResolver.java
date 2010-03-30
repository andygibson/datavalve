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

package org.fluttercode.spigot.provider.seam;

import java.io.Serializable;

import org.jboss.seam.core.Expressions;
import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Spigot parameter resolver that determines the parameter value by treating
 * the parameter as an EL expression. It uses Seams Expression instance to
 * evaluate the expression.
 * 
 * @author Andy Gibson
 * 
 */
public class SeamParameterResolver implements ParameterResolver, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory
			.getLogger(SeamParameterResolver.class);

	public boolean resolveParameter(
			ParameterizedDataProvider<? extends Object> dataset,
			Parameter parameter) {
		// log.debug("Resolving Seam Parameter : {}", parameter);
		Object result = Expressions.instance().createValueExpression(
				parameter.getName()).getValue();
		log.debug("Expression {} evaluated to {}", parameter.getName(), result);

		if (result != null) {
			// log.debug("Result type is {}", result.getClass().getName());
			parameter.setValue(result);
			return true;
		}
		return false;
	}

	public boolean acceptParameter(String parameter) {
		return parameter.startsWith("#{") && parameter.endsWith("}");
	}

}
