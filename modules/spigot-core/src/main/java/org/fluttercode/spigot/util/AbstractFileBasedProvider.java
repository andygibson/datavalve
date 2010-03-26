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

package org.fluttercode.spigot.util;

import java.io.File;

import org.fluttercode.spigot.impl.provider.AbstractDataProvider;

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
