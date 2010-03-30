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
