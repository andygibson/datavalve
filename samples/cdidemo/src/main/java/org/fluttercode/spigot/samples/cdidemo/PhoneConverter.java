package org.fluttercode.spigot.jsfdemo;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class PhoneConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		//we never need it
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		//value us a string phone number
		String sval = (String)value;
		
		return String.format("(%s) %s %s",sval.substring(0,3),sval.substring(3,6),sval.substring(6,10));
		
	}

}
