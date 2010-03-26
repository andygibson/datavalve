/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.jdataset;

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
