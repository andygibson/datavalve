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

package org.fluttercode.spigot.samples.wicketdemo.search;

import org.apache.wicket.PageParameters;
import org.fluttercode.spigot.dataset.ParameterizedDataset;
import org.fluttercode.spigot.provider.jpa.JpaDataProvider;
import org.fluttercode.spigot.provider.jpa.JpaQueryProvider;
import org.fluttercode.spigot.samples.wicketdemo.model.Person;

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
        provider.getOrderKeyMap().put("phone","p.phone");
        return new ParameterizedDataset<Person>(provider);
	}

}
