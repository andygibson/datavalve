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

package org.fluttercode.spigot;

import org.fluttercode.spigot.util.DataConverter;

import junit.framework.TestCase;

/**
 * Tests the {@link DataConverter} class responsible for converting JDBC values
 * to typed values
 * 
 * @author Andy Gibson
 * 
 */
public class DataConverterTest extends TestCase {

	public void testLongConversion() {
		assertEquals(0, DataConverter.getLongValue(null));
		assertEquals(null, DataConverter.getLong(null));
		assertEquals(4l, DataConverter.getLongValue("4"));
	}

	public void testIntConversion() {
		assertEquals(0, DataConverter.getIntegerValue(null));
		assertEquals(null, DataConverter.getInteger(null));
		assertEquals(4l, DataConverter.getIntegerValue("4"));
	}

}
