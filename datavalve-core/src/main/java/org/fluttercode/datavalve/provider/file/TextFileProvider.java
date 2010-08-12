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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.fluttercode.datavalve.Paginator;

/**
 * Abstract Text file provider that can be used to use a text file as a
 * datasource with an object definition per line.
 * 
 * @author Andy Gibson
 * 
 */
public abstract class TextFileProvider<T> extends AbstractFileBasedProvider<T> {

	private static final long serialVersionUID = 1L;

	public TextFileProvider(String fileName) {
		super(fileName);
	}

	private int countNumberOfLines(File file) {
		int count = 0;
		BufferedReader reader = null;
		try {
			try {
				reader = new BufferedReader(new FileReader(file));
				while (reader.readLine() != null) {
					count++;
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	protected Integer doFetchResultCount() {
		return countNumberOfLines(getFile());
	}

	// TODO change this implementation so it doesn't need to get the row count
	@Override
	protected List<T> doFetchResults(Paginator paginator) {
		Integer rowCount = paginator.getMaxRows() == null ? null : paginator
				.getMaxRows();
		int firstResult = paginator.getFirstResult();

		BufferedReader reader = null;
		List<T> results = new ArrayList<T>();
		String line;
		try {
			try {
				reader = new BufferedReader(new FileReader(getFile()));

				// Put this 'if' in as an extra measure, we don't want to use a
				// readline if firstresult = 0 since we want to start at the
				// beginning. Put this check in because we don't want to rely on
				// fail fast checks in the while statement.
				if (firstResult != 0) {
					while (firstResult != 0 && reader.readLine() != null) {
						firstResult--;
					}
				}

				while ((line = reader.readLine()) != null
						&& (rowCount == null || rowCount != 0)) {
					if (line != null) {
						results.add(createObjectFromLine(line));
					}
					if (rowCount != null) {
						rowCount--;
					}
				}
				// do we have any more lines?
				paginator.setNextAvailable(reader.readLine() != null);

			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;

	}

	protected abstract T createObjectFromLine(String line);
}
