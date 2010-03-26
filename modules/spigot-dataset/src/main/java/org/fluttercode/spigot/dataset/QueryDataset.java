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

import java.util.List;
import java.util.Map;

import org.fluttercode.spigot.ParameterResolver;
import org.fluttercode.spigot.provider.QueryDataProvider;

/**
 * @author Andy Gibson
 * 
 */
public class QueryDataset<T> extends
		GenericProviderDataset<T, QueryDataProvider<T>> implements
		QueryDataProvider<T> {

	private static final long serialVersionUID = 1L;
	 	
	public QueryDataset() {
		super();
	}
	
	public QueryDataset(QueryDataProvider<T> provider) {
		super(provider);
	}

	public void addRestriction(String restriction) {
		getProvider().addRestriction(restriction);
	}

	public boolean addRestriction(String restriction, Object value) {
		return getProvider().addRestriction(restriction, value);
	}

	public boolean addRestriction(String restriction, String testValue,
			String paramValue) {
		return getProvider().addRestriction(restriction, testValue, paramValue);
	}

	public boolean addRestriction(String restriction, Object testValue,
			Object paramValue) {
		return getProvider().addRestriction(restriction, testValue, paramValue);
	}

	public Map<String, String> getOrderKeyMap() {
		return getProvider().getOrderKeyMap();
	}

	public List<String> getRestrictions() {
		return getProvider().getRestrictions();
	}

	public void setOrderKeyMap(Map<String, String> orderKeyMap) {
		getProvider().setOrderKeyMap(orderKeyMap);
	}

	public void setRestrictions(List<String> restrictions) {
		getProvider().setRestrictions(restrictions);
	}

	public String getCountStatement() {
		return getProvider().getCountStatement();
	}

	public String getSelectStatement() {
		return getProvider().getSelectStatement();
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
