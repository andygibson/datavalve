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

package org.fluttercode.spigot.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class which handles the conversion of objects to particular data types.
 * Reusable through different parts of the framework and client data providers.
 * 
 * @author Andy Gibson
 * 
 */
public class DataConverter {

	private static Logger log = LoggerFactory.getLogger(DataConverter.class);
	private static Long longDefault = new Long(0);
	private static Integer intDefault = new Integer(0);

	private DataConverter() {
		// hide constructor
	}

	/**
	 * Converts value to a {@link Long} value, or returns null if the value
	 * cannot be converted or is null
	 * 
	 * @param value
	 *            Value to convert to a {@link Long} object
	 * @return The value as a {@link Long}
	 */
	public static Long getLong(Object value) {
		return getLong(value, null);
	}

	/**
	 * Converts value to a {@link Long} value. If it cannot be converted, then
	 * the default value is returned.
	 * 
	 * @param value
	 *            Value to convert to a {@link Long} object
	 * @param defaultValue
	 *            the value to return if the value is null or cannot be
	 *            converted
	 * @return The value as a {@link Long} or the default value if it cannot be
	 *         converted
	 */
	public static Long getLong(Object value, Long defaultValue) {

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Long) {
			return (Long) value;
		}

		if (value instanceof Integer) {
			return new Long((Integer) value);
		}

		if (value instanceof String) {
			return Long.valueOf((String) value);
		}

		if (value instanceof Byte) {
			return ((Byte) value).longValue();
		}

		return defaultValue;
	}

	/**
	 * Converts value to an {@link Integer} object, or returns null if it cannot
	 * be converted or the value is null.
	 * 
	 * @param value
	 *            Value to convert to an {@link Integer} object
	 * @return The value as an <code>Integer</code>
	 */
	public static Integer getInteger(Object value) {
		return getInteger(value, null);
	}

	/**
	 * Converts value to an <code>Integer</code> value. If it cannot be
	 * converted or is null then the default value is returned.
	 * 
	 * @param value
	 *            Value to convert to an <code>Integer</code> object
	 * @param defaultValue
	 *            the value to return if <code>value</code> is null or cannot be
	 *            converted
	 * @return The value as an <code>Integer</code>
	 */

	public static Integer getInteger(Object value, Integer defaultValue) {

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Long) {
			log.warn("Converting value of type long to integer");
			return ((Long) value).intValue();
		}

		if (value instanceof Integer) {
			return (Integer) value;
		}

		if (value instanceof String) {
			return Integer.valueOf((String) value);
		}

		if (value instanceof Byte) {
			return ((Byte) value).intValue();
		}

		return defaultValue;
	}

	/**
	 * Converts value to a <code>long</code>, or zero if the value cannot be
	 * converted or is null
	 * 
	 * @param value
	 *            the value to convert to a <code>long</code>
	 * @return the value as a <code>long</code>
	 */
	public static long getLongValue(Object value) {
		return getLong(value, longDefault).longValue();
	}

	/**
	 * Converts value to a <code>int</code>, or zero if the value cannot be
	 * converted or is null
	 * 
	 * @param value
	 *            the value to convert to a <code>int</code>
	 * @return the value as a <code>int</code>
	 */
	public static int getIntegerValue(Object value) {
		return getInteger(value, intDefault);
	}

	/**
	 * Converts value to a <code>Date</code>, or returns null if it cannot be
	 * converted or value is null
	 * 
	 * @param value
	 *            the value to convert to a <code>long</code>
	 * @param defaultValue
	 *            The default value to return if the value is null or cannot be
	 *            converted
	 * @return the value as a <code>long</code>
	 */
	public static Date getDate(Object value) {
		return getDate(value, null);
	}

	/**
	 * Converts value to a <code>Date</code>, or returns the default value. If
	 * the default value is not set then
	 * 
	 * @param value
	 *            the value to convert to a <code>long</code>
	 * @param defaultValue
	 *            The default value to return if the value is null or cannot be
	 *            converted
	 * @return the value as a <code>long</code>
	 */
	public static Date getDate(Object value, Date defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Date) {
			return (Date) value;
		}

		return defaultValue;

	}
}
