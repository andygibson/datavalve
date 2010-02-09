package org.jdataset.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jdataset.ParameterManager;
import org.jdataset.RestrictionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRestrictionManager implements RestrictionManager,
		Serializable {

	private static final long serialVersionUID = 1L;

	private int paramId;

	private ParameterManager parameterManager;

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(DefaultRestrictionManager.class);

	private List<String> restrictions = new ArrayList<String>();

	public DefaultRestrictionManager(ParameterManager parameterManager) {
		this.parameterManager = parameterManager;
	}

	public List<String> getRestrictions() {
		return new ArrayList<String>(restrictions);
	}

	public void setRestrictions(List<String> restrictions) {
		this.restrictions = new ArrayList<String>(restrictions);
	}

	public void add(String restriction) {
		restrictions.add(restriction);
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
			return addRestriction(syntax, testValue, paramValue);
		}
		return false;
	}

	public boolean addRestriction(String syntax, Object testValue,
			Object paramValue) {
		if (testValue != null) {
			String name = getNextParamName();
			syntax = syntax.replace(":param", ":" + name);
			add(syntax);
			parameterManager.addParameter(name, paramValue);
			return true;
		}
		return false;
	}

	public ParameterManager getParameterManager() {
		return parameterManager;
	}

	public void setParameterManager(ParameterManager parameterManager) {
		this.parameterManager = parameterManager;
	}

}
