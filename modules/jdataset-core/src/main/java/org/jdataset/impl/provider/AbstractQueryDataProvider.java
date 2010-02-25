package org.jdataset.impl.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdataset.Paginator;
import org.jdataset.Parameter;
import org.jdataset.impl.RestrictionBuilder;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		AbstractQLDataProvider<T> implements QueryDataProvider<T>,
		Serializable {

	private static final long serialVersionUID = 1L;

	private int paramId;

	private static Logger log = LoggerFactory
			.getLogger(AbstractQueryDataProvider.class);

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
		setCountStatement(String.format("select count(%s) from %s %s ",prefix,clazz.getSimpleName(),prefix));
		setSelectStatement(String.format("select %s from %s %s ",prefix,clazz.getSimpleName(),prefix));		
	}

	private String translateOrderKey(String orderKeyValue) {
		return getOrderKeyMap().get(orderKeyValue);
	}

	public void addRestriction(String restriction) {
		getRestrictions().add(restriction);
	}

	protected String getNextParamName() {
		return "_param_" + String.valueOf(paramId++);
	}

	public boolean addRestriction(String syntax, Object value) {
		return addRestriction(syntax, value, value);
	}

	public boolean addRestriction(String syntax, String testValue,
			String paramValue) {
		if (testValue != null && testValue.length() != 0) {
			return addRestriction(syntax, testValue,paramValue);
		}
		return false;
	}

	public boolean addRestriction(String syntax, Object testValue, Object paramValue) {
		if (testValue != null) {
			String name = getNextParamName();
			syntax = syntax.replace(":param", ":" + name);
			addRestriction(syntax);
			getParameters().put(name, paramValue);
			return true;
		}
		return false;
	}

	protected DataQuery buildDataQuery(String baseStatement,
			boolean includeOrdering, Paginator paginator) {
		DataQueryBuilder builder = new DataQueryBuilder();
		builder.setProvider(this);
		builder.setBaseStatement(baseStatement);
		if (includeOrdering) {
			builder.setOrderBy(getOrderKeyMap().get(paginator.getOrderKey()));
			builder.setOrderAscending(paginator.isOrderAscending());
		}
		return builder.build();
	}
	
}
