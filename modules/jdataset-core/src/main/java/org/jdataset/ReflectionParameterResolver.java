package org.jdataset;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


public class ReflectionParameterResolver implements ParameterResolver,Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object object;

	public ReflectionParameterResolver(Object object) {
		this.object = object == null ? this : object;
	}

	public ReflectionParameterResolver() {
		this(null);
	}

	public Object getObject() {
		return object;
	}

	public boolean resolveParameter(Parameter parameter) {
		String[] properties = parameter.getName().split("\\.");
		int index = 0;
		Object base = object;
		while (index < properties.length && base != null) {
			base = resolveProperty(base, properties[index]);
			index++;
		}
		if (base != null) {
			parameter.setValue(base);
			return true;
		}
		return false;
	}

	public Object resolveProperty(Object base, String property) {
		try {
			return org.apache.commons.beanutils.PropertyUtils.getProperty(base, property);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
