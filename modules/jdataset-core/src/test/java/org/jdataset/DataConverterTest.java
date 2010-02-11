package org.jdataset;

import junit.framework.TestCase;

public class DataConverterTest extends TestCase {
	
	public void testLongConversion() {		
		assertEquals(0,DataConverter.getLongValue(null));
		assertEquals(null,DataConverter.getLong(null));
		assertEquals(4l,DataConverter.getLongValue("4"));		
	}
	
	public void testIntConversion() {		
		assertEquals(0,DataConverter.getIntegerValue(null));
		assertEquals(null,DataConverter.getInteger(null));
		assertEquals(4l,DataConverter.getIntegerValue("4"));		
	}
	

}
