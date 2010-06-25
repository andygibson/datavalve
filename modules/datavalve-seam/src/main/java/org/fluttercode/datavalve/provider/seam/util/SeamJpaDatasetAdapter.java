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

package org.fluttercode.datavalve.provider.seam.util;

import org.fluttercode.datavalve.provider.jpa.JpaQueryProvider;
import org.fluttercode.datavalve.provider.seam.SeamJpaDataset;

/**
 * This is a utility class that subclasses the SeamJpaDataset and adds on
 * methods so the interface is the same as the default Seam entity query
 * interface. This class lets you slowly migrate to DataValve by replacing the
 * entity queries with this class and later changing it to a plain
 * SeamJpaDataset once your user interface has migrated to the slightly
 * different DataValve interface.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of entity this dataset returns
 */
public class SeamJpaDatasetAdapter<T> extends SeamJpaDataset<T> {

	private static final long serialVersionUID = 1L;

	public SeamJpaDatasetAdapter() {
		super();
		setMaxRows(10);
	}

	public SeamJpaDatasetAdapter(JpaQueryProvider<T> provider) {
		super(provider);
		setMaxRows(10);
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

	public void setEjbql(String ejbql) {
		getProvider().setSelectStatement(ejbql);
	}

	public void setCountEjbql(String countEjbql) {
		getProvider().setCountStatement(countEjbql);
	}
}
