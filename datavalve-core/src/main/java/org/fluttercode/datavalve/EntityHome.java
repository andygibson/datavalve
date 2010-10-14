package org.fluttercode.datavalve;

import java.io.Serializable;

public interface EntityHome<T> {
	
	T getEntity();
	void setEntity(T entity);
	Object getId();
	void setId(Object id);
	
	void insert();
	void cancel();
	void update();
	
	boolean isManaged();
	boolean isIdSet();
	
	
	
}
