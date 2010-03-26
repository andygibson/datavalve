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

package org.fluttercode.spigot.samples.cdidemo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.fluttercode.spigot.dataset.QueryDataset;
import org.fluttercode.spigot.samples.cdidemo.model.Person;

/**
 * Request scoped dataset that holds the info regarding the first result and
 * page size for the demo data. Values are popped into here using  
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
	public PersonSearchDataset(PersonSearchProvider provider) {
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
