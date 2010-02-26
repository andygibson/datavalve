package org.jdataset.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdataset.Paginator;
import org.jdataset.impl.provider.AbstractDataProvider;

public abstract class TextFileProvider<T> extends AbstractDataProvider<T> {

	private static final long serialVersionUID = 1L;

	//private static Logger log = LoggerFactory.getLogger(TextFileDataset.class);

	private final String fileName;
	private final File file;
	private final int lineCount;

	public TextFileProvider(String fileName) {
		super();
		this.file = new File(fileName);
		this.fileName = fileName;

		if (!file.exists()) {
			throw new IllegalArgumentException(String.format(
					"File '%s' does not exist", fileName));
		}

		lineCount = countNumberOfLines(file);
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
		return countNumberOfLines(file);
	}

	//TODO change this implementation so it doesn't need to get the row count	
	@Override
	public List<T> fetchResults(Paginator paginator) {
		int rowCount = paginator.getMaxRows() == null ? fetchResultCount()
				: paginator.getMaxRows();
		int firstResult = paginator.getFirstResult();
		
		BufferedReader reader;
		List<T> results = new ArrayList<T>();
		String line;
		try {
			reader = new BufferedReader(new FileReader(file));

			// Put this 'if' in as an extra measure, we don't want to use a
			// readline if firstresult = 0 since we want to start at the
			// beginning. Put this check in because we don't want to rely on
			// fail fast checks in the while statement.
			if (firstResult != 0) {
				while (firstResult != 0 && reader.readLine() != null) {
					firstResult--;
				}
			}

			while ((line = reader.readLine()) != null && rowCount != 0) {
				if (line != null) {
					results.add(createObjectFromLine(line));
				}
				rowCount--;
			}
			//do we have any more lines?
			paginator.setNextAvailable(reader.readLine() != null);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;

	}

	protected abstract T createObjectFromLine(String line);

	public String getFileName() {
		return fileName;
	}

}
