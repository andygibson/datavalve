package org.fluttercode.spigot.datafactory;

import org.fluttercode.spigot.testing.TestDataFactory;

import junit.framework.TestCase;

public class TestDataFactoryTest extends TestCase {

	public void testRandomText() {

		for (int i = 0; i < 1000; i++) {

			String ret = TestDataFactory.getRandomText(200, 20);
			System.out.println(ret);
			System.out.println("");

			assertTrue("Length is " + ret.length(), ret.length() >= 20);
			assertTrue("Length is " + ret.length(), ret.length() <= 200);

		}

	}

}
