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

package org.fluttercode.datavalve.samples.wicketdemo.dataprovider;

import org.apache.wicket.PageParameters;
import org.fluttercode.datavalve.dataset.Dataset;
import org.fluttercode.datavalve.dataset.ObjectDataset;
import org.fluttercode.datavalve.provider.jpa.JpaDataProvider;
import org.fluttercode.datavalve.provider.jpa.JpaQueryProvider;
import org.fluttercode.datavalve.samples.wicketdemo.model.Person;

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
        return new Dataset<Person,JpaDataProvider<Person>>(people);
	}

}
