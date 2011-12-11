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

package org.fluttercode.datavalve.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.datavalve.Paginator;
import org.fluttercode.datavalve.provider.util.DataQuery;
import org.fluttercode.datavalve.provider.util.DataQueryBuilder;

/**
 * Abstract class for Query driven datasets that implements most of the methods
 * in the {@link QueryDataProvider} interface. It adds features for defining
 * select/count statements, and translating orderKey values into other
 * representations. It also defines common methods for defining an order clause
 * and building statements based on the Sql/Ejbql structure.
 * <p>
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
		setSelectStatement(String.format("select %s from %s %s ", prefix,
				clazz.getSimpleName(), prefix));
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

	public boolean addRestrictionStr(String syntax, String value) {
		return addRestrictionStr(syntax, value, value);
	}

	public boolean addRestrictionStr(String syntax, String paramValue,
			String testValue) {
		if (isValidTestValue(testValue, true)) {
			return addRestriction(syntax, paramValue, testValue);
		}
		return false;
	}

	public boolean addRestriction(String syntax, Object paramValue,
			Object testValue) {
		if (isValidTestValue(testValue, false)) {
			if (paramValue instanceof Collection<?>) {
				
				addCollectionRestriction(syntax,(Collection<?>) paramValue);
			} else {
				addRestrictionForDefault(syntax, paramValue);
			}
			return true;
		}
		return false;
	}

	/**
	 * Adds a restriction based on a collection. It is assumed that the syntax
	 * used is acceptable for a collection, for example, using
	 * <code> field in (:param)</code> and passing in a collection.
	 * <p/>
	 * For each item in the collection, see if it is valid and if so, add it to
	 * the list of parameters to be used in the statement.
	 * 
	 * @param syntax
	 *            syntax to use in the restriction
	 * @param paramValue
	 */
	private void addCollectionRestriction(String syntax,
			Collection<?> paramValues) {
		String paramList = "";
		for (Object o : paramValues) {
			if (isValidTestValue(o, false)) {
				//prefix with comma if its not the first
				if (paramList.length() != 0) {
					paramList = paramList + ",";
				}
				String name = getNextParamName();
				paramList = paramList + ":" + name;
				getParameters().put(name, o);
			}
		}		
		syntax = syntax.replace(":param", paramList);
		addRestriction(syntax);
	}

	/**
	 * Adds a restriction based on a parameter value that is expected to be a
	 * typical value (String, Long, int, boolean, etc) instead of a more exotic
	 * parameter type such as as collection. This method assumes any pre-add
	 * checks have been done for null params or test params etc,
	 * 
	 * @param syntax
	 *            Syntax of the restriction
	 * @param paramValue
	 *            Value to use,
	 */
	private void addRestrictionForDefault(String syntax, Object paramValue) {
		String name = getNextParamName();
		syntax = syntax.replace(":param", ":" + name);
		addRestriction(syntax);
		getParameters().put(name, paramValue);
	}

	protected boolean isValidTestValue(Object obj, boolean testStrings) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String && testStrings) {
			String s = (String) obj;
			return s.length() != 0;
		}
		if (obj instanceof Collection<?>) {
			if (((Collection<?>) obj).size() == 0) {
				return false;
			}
			// check it contains a valid test value
			for (Object o : (Collection<?>) obj) {
				// avoid recursion by checking for self
				if (o != obj) {
					if (isValidTestValue(o, testStrings)) {
						return true;
					}
				}
			}
		}
		return true;
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
