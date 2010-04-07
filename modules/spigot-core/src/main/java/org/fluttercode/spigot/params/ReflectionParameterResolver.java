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

package org.fluttercode.spigot.params;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a {@link ParameterResolver} that looks up values in the
 * referenced object based on run time reflection. The resolver expects a
 * parameter to be named as per the JavaBean specification for a particular
 * property on the object.
 * <p>
 * For example, a parameter called name should be specified as
 * <code>:name</code> and the property on the object be called
 * <code>getName()</code>.
 * 
 * @author Andy Gibson
 * 
 */
public class ReflectionParameterResolver implements ParameterResolver,Serializable {

	private static Logger log = LoggerFactory.getLogger(ReflectionParameterResolver.class);
	
	private static final long serialVersionUID = 1L;

	private Object object;

	public ReflectionParameterResolver(Object object) {
		this.object = object == null ? this : object;
	}

	public ReflectionParameterResolver() {
		this(null);
	}

	public Object getObject() {
		return object;
	}

	public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,Parameter parameter) {
		String name=parameter.getName().substring(1);//remove the starting ':'
		
		String[] properties = name.split("\\.");
		int index = 0;
		Object base = object;
		while (index < properties.length && base != null) {
			//log.debug("Resolving property {} on base of {}",properties[index],base);
			base = resolveProperty(base, properties[index]);
			index++;
		}
		log.debug("{} resolved to '{}'",parameter.getName(),base);
		if (base != null) {			
			parameter.setValue(base);
			return true;
		}
		return false;
	}

	public Object resolveProperty(Object base, String property) {
		try {
			return org.apache.commons.beanutils.PropertyUtils.getProperty(base,
					property);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean acceptParameter(String parameter) {
		return parameter.startsWith(":");
	}
}
