package org.jdataset;

public interface OrderManager<T> {
	
	T getOrderValue(String key);
	void add(String key,T value);
	boolean isEmpty();
	boolean containsKey(String key);
}
