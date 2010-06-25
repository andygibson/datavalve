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

package org.fluttercode.datavalve.samples.wicketdemo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.fluttercode.datavalve.samples.wicketdemo.custom.FileCustomPaginatorPage;
import org.fluttercode.datavalve.samples.wicketdemo.custom.JpaCustomPaginatorPage;
import org.fluttercode.datavalve.samples.wicketdemo.custom.JdbcCustomPaginatorPage;
import org.fluttercode.datavalve.samples.wicketdemo.dataprovider.JpaDataProviderPage;
import org.fluttercode.datavalve.samples.wicketdemo.dataprovider.SqlDataProviderPage;
import org.fluttercode.datavalve.samples.wicketdemo.search.JpaSearchPage;
import org.fluttercode.datavalve.samples.wicketdemo.search.SqlSearchPage;

/**
 * @author Andy Gibson
 * 
 */
public class HeaderLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	public HeaderLinkPanel(String id) {
		super(id);
				
		add(new BookmarkablePageLink<WebPage>("jpaDatasetLink",JpaCustomPaginatorPage.class));
		add(new BookmarkablePageLink<WebPage>("jdbcDatasetLink",JdbcCustomPaginatorPage.class));
		add(new BookmarkablePageLink<WebPage>("fileDatasetLink",FileCustomPaginatorPage.class));
		add(new BookmarkablePageLink<WebPage>("jpaProviderLink",JpaDataProviderPage.class));
		add(new BookmarkablePageLink<WebPage>("sqlProviderLink",SqlDataProviderPage.class));
		
		add(new BookmarkablePageLink<WebPage>("jpaSearchLink",JpaSearchPage.class));
		add(new BookmarkablePageLink<WebPage>("sqlSearchLink",SqlSearchPage.class));
		
	}
	
	

	

}
