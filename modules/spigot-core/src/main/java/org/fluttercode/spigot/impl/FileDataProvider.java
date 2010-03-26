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

package org.fluttercode.spigot.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//TODO Need to create more tests for this class (i.e. setting the path and so on)
/**
 * Dataset the represents a directory listing as a collection of {@link File}
 * objects.
 * 
 * @author Andy Gibson
 * 
 */
public class FileDataProvider extends InMemoryDataProvider<File> {

	private static final long serialVersionUID = 1L;

	private File sourceDirectory;
	private boolean includeDirectories = false;
	
	private final transient FileFilter fileFilter = new FileFilter() {

		public boolean accept(File file) {
			return file.isFile() || (includeDirectories && file.isDirectory());
		}
	};

	public FileDataProvider(String path) {
		this(createDirectoryFileFromPath(path));
	}

	public FileDataProvider(File targetPath) {

		if (targetPath == null) {
			return;
		}

		// defensively copy the path over
		File temp = targetPath;
		if (temp.isFile()) {
			temp = temp.getParentFile();
		}

		if (!temp.isDirectory()) {
			throw new IllegalArgumentException(
					"File passed to constructor is not a directory : "
							+ temp.getAbsolutePath());
		}

		sourceDirectory = new File(temp.getAbsolutePath());
	}

	public String getPath() {
		return sourceDirectory.getAbsolutePath();
	}

	public void setPath(String path) {
		sourceDirectory = null;
		createDirectoryFileFromPath(path);
	}

	private static File createDirectoryFileFromPath(String path) {
		if (path == null) {
			return null;
		}
		File temp = new File(path);
		if (!temp.exists()) {
			throw new IllegalArgumentException("Invalid path : " + path);
		}

		if (temp.isFile()) {
			temp = temp.getParentFile();
		}
		return temp;
	}

	public void setIncludeDirectories(boolean includeDirectories) {
		this.includeDirectories = includeDirectories;
	}

	public boolean getIncludeDirectories() {
		return includeDirectories;
	}

	@Override
	protected List<File> fetchBackingData() {
		if (sourceDirectory != null) {
			return Arrays.asList(sourceDirectory.listFiles(fileFilter));
		}
		return Collections.emptyList();
	}
}
