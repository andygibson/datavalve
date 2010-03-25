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

package org.jdataset.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.impl.provider.AbstractDataProvider;

/**
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
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null) {
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Integer fetchResultCount() {
		return countNumberOfLines(getFile());
	}

	// TODO change this implementation so it doesn't need to get the row count
	@Override
	public List<T> fetchResults(Paginator paginator) {
		Integer rowCount = paginator.getMaxRows() == null ? null : paginator
				.getMaxRows();
		int firstResult = paginator.getFirstResult();

		BufferedReader reader;
		List<T> results = new ArrayList<T>();
		String line;
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

		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;

	}

	protected abstract T createObjectFromLine(String line);
}
