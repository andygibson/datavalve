/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
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
