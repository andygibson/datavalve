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

package org.fluttercode.spigot.samples.wicketdemo.dataprovider;

import org.apache.wicket.PageParameters;
import org.fluttercode.spigot.dataset.Dataset;
import org.fluttercode.spigot.dataset.ObjectDataset;
import org.fluttercode.spigot.provider.jpa.JpaDataProvider;
import org.fluttercode.spigot.provider.jpa.JpaQueryProvider;
import org.fluttercode.spigot.samples.wicketdemo.model.Person;

/**
 * @author Andy Gibson
 * 
 */
public class JpaDataProviderPage extends AbstractDataProviderPage {

	public JpaDataProviderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public ObjectDataset<Person> createDataset() {
        JpaDataProvider<Person> people = new JpaQueryProvider<Person>();
        people.setCountStatement("select count(p) from Person p");
        people.setSelectStatement("select p from Person p");
        people.setEntityManager(getWicketApp().createEntityManager());
        people.getOrderKeyMap().put("id", "p.id");
        people.getOrderKeyMap().put("name", "p.lastName,p.firstName");
        people.getOrderKeyMap().put("phone", "p.phone");
        return new Dataset<Person>(people);
	}

}
