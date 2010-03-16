package org.jdataset.util;

import java.io.File;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.impl.provider.AbstractDataProvider;

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
