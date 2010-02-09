package org.jdataset.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jdataset.OrderManager;

public class DefaultOrderHandler<T> implements OrderManager<T>, Serializable{

	private Map<String, T> orderKeyMap = new HashMap<String, T>();

	public T getOrderValue(String key) {
		return orderKeyMap.get(key);
	}

	public void add(String key, T value) {
		orderKeyMap.put(key, value);
	}

	public boolean containsKey(String key) {
		return orderKeyMap.containsKey(key);
	}

	public boolean isEmpty() {
		return orderKeyMap.isEmpty();
	}

}
