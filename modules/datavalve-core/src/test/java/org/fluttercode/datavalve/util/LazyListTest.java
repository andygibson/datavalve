/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.fluttercode.datavalve.util.LazyList;

/**
 * @author Andy Gibson
 * 
 */
public class LazyListTest extends TestCase {

	private LazyList<Integer, Element> list;

	public class Element {
		int id;
		String value;

		Element(int id, String value) {
			this.id = id;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + id;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Element other = (Element) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		private LazyListTest getOuterType() {
			return LazyListTest.this;
		}

	}

	protected String generateText(int index) {
		return "Value is " + index;
	}

	protected void setUp() throws Exception {

		list = new LazyList<Integer, Element>() {

			@Override
			protected Integer fetchKeyForValue(Element value) {
				return value.id;
			}

			@Override
			protected Element fetchValueForKey(Integer key) {
				return new Element(key, "Value is " + key.toString());
			}

		};

		for (int i = 0; i < 100; i++) {
			list.addKey(i);
		}

	}

	public void testSetup() {
		assertEquals(100, list.size());
		assertEquals("Value is 76", list.get(76).value);
		assertEquals(1, list.fillCount());
	}

	public void testAdd() {
		Element e = new Element(10000, "Some Text");
		list.add(new Element(10000, "Some Text"));
		Element last = list.get(list.size() - 1);
		assertEquals(last, e);

	}

	public void testClear() {
		list.clear();
		assertEquals(0, list.size());
	}

	public void testRetain() {
		List<Element> c = new ArrayList<Element>();
		c.add(new Element(15, generateText(15)));
		list.retainAll(c);
		assertEquals(1, list.size());
		assertEquals(15, list.get(0).id);
	}

	public void testAddAll() {
		List<Element> c = new ArrayList<Element>();
		c.add(new Element(150, generateText(150)));
		list.addAll(c);
		assertEquals(101, list.size());
		assertEquals(150, list.get(100).id);
	}

	public void testRemove() {
		list.remove(50);
		assertEquals(99, list.size());
		assertEquals(51, list.get(50).id);

	}
}
