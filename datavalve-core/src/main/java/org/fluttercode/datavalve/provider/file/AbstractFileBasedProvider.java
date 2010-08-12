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

import java.io.File;

import org.fluttercode.datavalve.provider.AbstractDataProvider;

/**
 * @author Andy Gibson
 * 
 */
public abstract class AbstractFileBasedProvider<T> extends AbstractDataProvider<T> {

	private static final long serialVersionUID = 1L;

	// private static Logger log =
	// LoggerFactory.getLogger(TextFileDataset.class);

	private final String fileName;
	private final File file;

	public AbstractFileBasedProvider(String fileName) {
		super();
		this.file = new File(fileName);
		this.fileName = fileName;

		if (!file.exists()) {
			throw new IllegalArgumentException(String.format(
					"File '%s' does not exist", fileName));
		}
	}

	public String getFileName() {
		return fileName;
	}
	
	public File getFile() {
		return file;
	}
}
