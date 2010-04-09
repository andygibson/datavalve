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

package org.fluttercode.spigot.provider.file;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Andy Gibson
 * 
 */
public abstract class CommaDelimitedProvider<T> extends TextFileProvider<T> {

	private static final long serialVersionUID = 1L;

	private int paddingLength = 0;

	public CommaDelimitedProvider(String filename) {
		super(filename);
	}

	@Override
	protected T createObjectFromLine(String line) {
		String[] columns = padToLength(line.split(","));

		return createObjectFromColumns(columns);
	}

	/**
	 * Takes an array of columns and pads it with nulls until it has a certain
	 * length.
	 * 
	 * @param split
	 *            array of columns
	 * @return padded array of columns
	 */
	private String[] padToLength(String[] split) {
		if (paddingLength < split.length) {
			return split;
		}
		return Arrays.copyOf(split, paddingLength);
	}

	protected abstract T createObjectFromColumns(String[] columns);

	public void setPaddingLength(int paddingLength) {
		this.paddingLength = paddingLength;
	}

	public int getPaddingLength() {
		return paddingLength;
	}
}
