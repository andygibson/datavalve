/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.cdidemo;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 * @author Andy Gibson
 * 
 */
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
