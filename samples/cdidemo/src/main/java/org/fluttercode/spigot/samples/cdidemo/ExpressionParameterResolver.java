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

package org.fluttercode.spigot.samples.cdidemo;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.fluttercode.spigot.Parameter;
import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EL parameter resolver for the CDI demo in 0.9 Beta. This will probably end up
 * being the basis for the final EL Expression as we bundle it into its own
 * module.
 * 
 * @author Andy Gibson
 * 
 */
public class ExpressionParameterResolver implements ParameterResolver {

	private static ExpressionFactory ef = ExpressionFactory.newInstance();
	private static Logger log = LoggerFactory
			.getLogger(ExpressionParameterResolver.class);

	public boolean acceptParameter(String name) {
		return name.startsWith("#{") && name.endsWith("}");
	}

	public boolean resolveParameter(
			ParameterizedDataProvider<? extends Object> dataset,
			Parameter parameter) {
		ELContext elc = FacesContext.getCurrentInstance().getELContext();
		ValueExpression exp = ef.createValueExpression(elc,
				parameter.getName(), Object.class);

		Object v = exp.getValue(elc);
		log.debug("Resolving {} to {}", parameter.getName(), v);
		if (v != null) {
			parameter.setValue(v);
			return true;
		}
		return false;
	}

}
