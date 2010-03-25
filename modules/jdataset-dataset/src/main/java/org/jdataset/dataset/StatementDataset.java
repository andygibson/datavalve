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

package org.jdataset.dataset;

import java.util.Map;

import org.jdataset.ParameterResolver;
import org.jdataset.provider.StatementDataProvider;


/**
 * @author Andy Gibson
 * 
 */
public class StatementDataset<T> extends GenericProviderDataset<T,StatementDataProvider<T>> implements StatementDataProvider<T> {

	private static final long serialVersionUID = 1L;

	public StatementDataset(StatementDataProvider<T> provider) {
		super(provider);
	}

	public String getCountStatement() {
		return getProvider().getCountStatement();
	}

	public String getSelectStatement() {
		return getProvider().getCountStatement();
	}

	public void init(Class<? extends Object> clazz, String prefix) {
		getProvider().init(clazz, prefix);		
	}

	public void setCountStatement(String countStatement) {
		getProvider().setCountStatement(countStatement);		
	}

	public void setSelectStatement(String selectStatement) {
		getProvider().setSelectStatement(selectStatement);		
	}

	public void addParameter(String name, Object value) {
		getProvider().addParameter(name, value);
	}

	public void addParameterResolver(ParameterResolver resolver) {
		getProvider().addParameterResolver(resolver);
	}

	public Map<String, Object> getParameters() {
		return getProvider().getParameters();
	}

	public Object resolveParameter(String name) {
		return getProvider().resolveParameter(name);
	}

	public void setParameters(Map<String, Object> parameters) {
		getProvider().setParameters(parameters);
	}

	public Integer fetchResultCount() {
		return getProvider().fetchResultCount();
	}
}
