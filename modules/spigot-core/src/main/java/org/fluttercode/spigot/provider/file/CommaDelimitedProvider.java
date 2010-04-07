/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.provider.file;

/**
 * @author Andy Gibson
 * 
 */
public abstract class CommaDelimitedProvider<T> extends TextFileProvider<T>{

	private static final long serialVersionUID = 1L;

	public CommaDelimitedProvider(String filename) {
		super(filename);
	}
	
	
	@Override
	protected T createObjectFromLine(String line) {
		String[] columns = line.split(",");
		return createObjectFromColumns(columns);
	}

	protected abstract T createObjectFromColumns(String[] columns);
}
