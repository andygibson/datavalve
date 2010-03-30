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

package org.fluttercode.spigot.dataset;

import java.io.Serializable;

import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.params.Parameter;
import org.fluttercode.spigot.provider.ParameterizedDataProvider;

/**
 * @author Andy Gibson
 * 
 */
public class TestingParameterResolver implements ParameterResolver,Serializable {

	private static final long serialVersionUID = 1L;
	
	public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,Parameter parameter) {

		if (parameter.getName().equals("id")) {
			parameter.setValue("value_id");
			return true;
		}

		if (parameter.getName().equals("person.firstName")) {
			parameter.setValue("value_firstName");
			return true;
		}

		return false;

	}

	public boolean acceptParameter(String parameter) {
		return true;
	}

}
