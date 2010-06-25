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

package org.fluttercode.datavalve.samples.cdidemo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.fluttercode.datavalve.dataset.QueryDataset;
import org.fluttercode.datavalve.provider.QueryDataProvider;
import org.fluttercode.datavalve.samples.cdidemo.model.Person;

/**
 * Request scoped dataset for the demo. The page holds the state of the first
 * results, page size and ordering info and they are put back here on postback.
 * 
 * @author Andy Gibson
 * 
 */
@Named("personSearch")
@RequestScoped
public class PersonSearchDataset extends QueryDataset<Person> {

	private static final long serialVersionUID = 1L;

	// constructor required for CDI proxybility
	public PersonSearchDataset() {
		super();
	}

	@Inject
	public PersonSearchDataset(QueryDataProvider<Person> provider) {
		super(provider);
		setOrderKey("id");
		setMaxRows(10);
	}

	@Override
	@Produces
	@Named("personResults")
	public List<Person> getResultList() {
		// this function is overridden so we can mark it as a producer that
		// produces the results
		return super.getResultList();
	}
}
