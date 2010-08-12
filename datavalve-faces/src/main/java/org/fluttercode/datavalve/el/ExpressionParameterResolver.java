/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.fluttercode.datavalve.ParameterResolver;
import org.fluttercode.datavalve.params.Parameter;
import org.fluttercode.datavalve.provider.ParameterizedDataProvider;
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
