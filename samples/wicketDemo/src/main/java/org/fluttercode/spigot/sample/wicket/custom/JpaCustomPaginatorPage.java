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

package org.jdataset.sample.wicket.custom;

import org.apache.wicket.PageParameters;
import org.jdataset.dataset.Dataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.provider.jpa.JpaDataProvider;
import org.jdataset.impl.provider.jpa.JpaQueryProvider;
import org.phonelist.model.Person;

/**
 * @author Andy Gibson
 * 
 */
public class JpaCustomPaginatorPage extends AbstractCustomPaginatorPage {

	public JpaCustomPaginatorPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
		
        JpaDataProvider<Person> people = new JpaQueryProvider<Person>();
        people.setCountStatement("select count(p) from Person p");
        people.setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        return new Dataset<Person>(people);
	}
	

}