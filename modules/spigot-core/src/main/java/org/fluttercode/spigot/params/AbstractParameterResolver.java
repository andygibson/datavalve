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

import org.fluttercode.spigot.ParameterResolver;

/**
 * Abstract class that implements {@link ParameterResolver} and
 * {@link Serializable} so we can extend this for anonymous classes without
 * worrying about serialization issues. It also implements one or two helper
 * methods that you can re-use to determine if a parameter name is of a certain
 * format.
 * <p>
 * Override this class and implement the two interface methods to provide a
 * custom parameter resolver.
 * 
 * @author Andy Gibson
 * 
 */
public abstract class AbstractParameterResolver implements ParameterResolver,
		Serializable {

	private static final long serialVersionUID = 1L;
	
	public final boolean isElExpression(String name) {
		if (name != null) {
			return name.startsWith("#{") && name.endsWith("}");
		}
		return false;
	}

	public final boolean isCommaExpression(String name) {
		if (name != null) {
			return name.startsWith(":");
		}
		return false;
	}

}
