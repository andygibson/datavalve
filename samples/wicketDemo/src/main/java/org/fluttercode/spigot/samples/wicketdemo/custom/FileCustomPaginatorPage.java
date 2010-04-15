/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.wicketdemo.custom;

import java.net.URL;

import org.apache.wicket.PageParameters;
import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.provider.file.CommaDelimitedProvider;
import org.fluttercode.spigot.samples.wicketdemo.model.Person;

/**
 * @author Andy Gibson
 * 
 */
public class FileCustomPaginatorPage extends AbstractCustomPaginatorPage {

	public FileCustomPaginatorPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {

		URL url = getWicketApp().getClass().getResource("testData100.csv");

		String filename = url.getPath();
		CommaDelimitedProvider<Person> personProvider = new CommaDelimitedProvider<Person>(
				filename) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Person doCreateObjectFromColumns(String[] columns) {
				return new Person(Long.valueOf(columns[0]), columns[1],
						columns[2], columns[3]);
			}

		};
		return new Dataset<Person, CommaDelimitedProvider<Person>>(
				personProvider);
	}

}
