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

package org.fluttercode.datavalve.provider.file;

import java.util.Arrays;

/**
 * Provider that returns data from a comma delimited file using a
 * {@link ColumnarRowMapper} instance to convert an array of string values into
 * an object.
 * 
 * @author Andy Gibson
 * 
 */
public class CommaDelimitedProvider<T> extends TextFileProvider<T> {

	private static final long serialVersionUID = 1L;

	private int paddingLength = 0;

	private ColumnarRowMapper<T> rowMapper;

	public CommaDelimitedProvider(String filename,
			ColumnarRowMapper<T> rowMapper) {
		super(filename);
		this.rowMapper = rowMapper;
	}

	public CommaDelimitedProvider(String filename) {
		this(filename, null);
	}

	@Override
	protected T createObjectFromLine(String line) {
		String[] columns = padToLength(line.split(","));

		return createObjectFromColumns(columns);

	}

	/**
	 * Creates a new instance of a data object and populates it with the data
	 * from the columns array.
	 * 
	 * @param columns
	 *            List of column values
	 * @return new instance of data object built from the columns values
	 */
	private T createObjectFromColumns(String[] columns) {
		T result = doCreateObjectFromColumns(columns);
		if (result != null) {
			return result;
		}
		if (rowMapper == null) {
			throw new NullPointerException(
					"Rowmapper in comma delimited provider is unassigned");
		} 
		return rowMapper.mapRow(columns);
	}

	/**
	 * Method to create an instance of data from the column array of string.
	 * Default implementation returns null but this method can be overriden in
	 * subclasses to allow for extension using inheritance as opposed to
	 * extendision using the {@link CommaDelimitedProvider#rowMapper} attribute.
	 * 
	 * @param columns
	 *            Array of columns
	 * @return an instance of T built from the columnar data. If null is
	 *         returned, then the rowmapper is used to create the entity.
	 */
	protected T doCreateObjectFromColumns(String[] columns) {
		return null;
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

	public void setPaddingLength(int paddingLength) {
		this.paddingLength = paddingLength;
	}

	public int getPaddingLength() {
		return paddingLength;
	}
	
	 public ColumnarRowMapper<T> getRowMapper() {
		return rowMapper;
	}
	 public void setRowMapper(ColumnarRowMapper<T> rowMapper) {
		this.rowMapper = rowMapper;
	}
}
