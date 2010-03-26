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

package org.jdataset.seam.util;

import java.util.List;

import org.jdataset.seam.SeamJpaNativeDataset;

/**
 * This is a utility class that subclasses the SeamJpaNativeDataset and adds on
 * methods so the interface is the same as the default Seam entity query
 * interface. This class lets you slowly migrate to JDataset by replacing the
 * entity queries with this class and later changing it to a plain
 * SeamJpaNativeDataset once your user interface has migrated to the slightly
 * different JDataset interface.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of entity this dataset returns
 */
public class SeamJpaNativeDatasetAdapter<T> extends SeamJpaNativeDataset<T> {

	private static final long serialVersionUID = 1L;
	
	public List<T> getResultList() {
		return super.getResultList();
	}

	public int getActivePage() {
		return super.getPage();
	}

	public boolean getPreviousExists() {
		return super.isPreviousAvailable();
	}

	public boolean getNextExists() {
		return super.isNextAvailable();
	}
}
