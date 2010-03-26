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

package org.fluttercode.spigot.impl.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.spigot.Paginator;
import org.fluttercode.spigot.provider.QueryDataProvider;

/**
 * Abstract class for Query driven datasets that implements most of the methods
 * in the {@link QueryDataProvider} interface. It adds features for defining
 * select/count statements, and translating orderKey values into other
 * representations. It also defines common methods for defining an order clause
 * and building statements based on the Sql/Ejbql structure.
 *<p>
 * Typically, the orderKey translates to an order value through the OrderKeyMap.
 * However, we wrap this behaviour in the {@link #translateOrderKey(String)}
 * method. This can be overridden if you want to change how we translate
 * orderKey values.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of object this dataset contains.
 */
public abstract class AbstractQueryDataProvider<T> extends
		AbstractQLDataProvider<T> implements QueryDataProvider<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private int paramId;

	private Map<String, String> orderKeyMap = new HashMap<String, String>();
	private List<String> restrictions = new ArrayList<String>();

	public Map<String, String> getOrderKeyMap() {
		return orderKeyMap;
	}

	public void setOrderKeyMap(Map<String, String> orderKeyMap) {
		this.orderKeyMap = orderKeyMap;
	}

	public List<String> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<String> restrictions) {
		this.restrictions = restrictions;
	}

	public void init(Class<? extends Object> clazz, String prefix) {
		setCountStatement(String.format("select count(%s) from %s %s ", prefix,
				clazz.getSimpleName(), prefix));
		setSelectStatement(String.format("select %s from %s %s ", prefix, clazz
				.getSimpleName(), prefix));
	}

	/**
	 * Returns the order fields for a given order key pair. Default
	 * implementation works off the
	 * {@link AbstractQueryDataProvider#orderKeyMap} {@link Map} property.
	 * <p/>
	 * Can be overridden to handle custom specific mappings.
	 * 
	 * @param orderKeyValue
	 * @return
	 */
	protected String translateOrderKey(String orderKeyValue) {
		return getOrderKeyMap().get(orderKeyValue);
	}

	protected String getNextParamName() {
		return "_param_" + String.valueOf(paramId++);
	}

	public void addRestriction(String restriction) {
		getRestrictions().add(restriction);
	}

	public boolean addRestriction(String syntax, Object value) {
		return addRestriction(syntax, value, value);
	}

	public boolean addRestriction(String syntax, String testValue,
			String paramValue) {
		if (testValue != null && testValue.length() != 0) {
			return addRestriction(syntax, testValue, paramValue);
		}
		return false;
	}

	public boolean addRestriction(String syntax, Object testValue,
			Object paramValue) {
		if (testValue != null) {
			String name = getNextParamName();
			syntax = syntax.replace(":param", ":" + name);
			addRestriction(syntax);
			getParameters().put(name, paramValue);
			return true;
		}
		return false;
	}

	/**
	 * Constructs and returns a {@link DataQueryBuilder} instance. Override this
	 * to return alternative query builders that have different functions or to
	 * change attributes in the one returned by default.
	 * 
	 * @return A DataQueryBuilder to use for creating the {@link DataQuery}
	 */
	protected DataQueryBuilder createDataQueryBuilder() {
		return new DataQueryBuilder();
	}

	protected DataQuery buildDataQuery(String baseStatement,
			boolean includeOrdering, Paginator paginator) {
		DataQueryBuilder builder = new DataQueryBuilder();
		builder.setProvider(this);
		builder.setBaseStatement(baseStatement);
		if (includeOrdering) {
			builder.setOrderBy(translateOrderKey(paginator.getOrderKey()));
			builder.setOrderAscending(paginator.isOrderAscending());
		}
		return builder.build();
	}

}
