package org.jdataset.impl;

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
