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

package org.fluttercode.spigot.sample.wicket.search;

import org.apache.wicket.PageParameters;
import org.fluttercode.spigot.dataset.ParameterizedDataset;
import org.fluttercode.spigot.impl.provider.jpa.JpaDataProvider;
import org.fluttercode.spigot.impl.provider.jpa.JpaQueryProvider;
import org.phonelist.model.Person;

/**
 * @author Andy Gibson
 * 
 */
public class JpaSearchPage extends AbstractSearchPage {

	public JpaSearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ParameterizedDataset<Person> createDataset() {
        JpaDataProvider<Person> provider = new JpaQueryProvider<Person>();
        provider.setCountStatement("select count(p) from Person p");
        provider.setSelectStatement("select p from Person p");
        provider.addRestriction("upper(p.firstName) like upper(:firstNameValue)");
        provider.addRestriction("upper(p.lastName) like upper(:lastNameValue)");
        provider.addRestriction("p.phone like :phoneValue");
        provider.addRestriction("p.id = :id");
        provider.setEntityManager(getWicketApp().createEntityManager());
        provider.getOrderKeyMap().put("id","p.id");
        provider.getOrderKeyMap().put("name","p.lastName,p.firstName");
        return new ParameterizedDataset<Person>(provider);
	}

}
