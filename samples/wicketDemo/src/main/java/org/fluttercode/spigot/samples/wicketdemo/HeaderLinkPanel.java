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

package org.fluttercode.spigot.samples.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.fluttercode.spigot.samples.wicket.custom.FileCustomPaginatorPage;
import org.fluttercode.spigot.samples.wicket.custom.JpaCustomPaginatorPage;
import org.fluttercode.spigot.samples.wicket.custom.JdbcCustomPaginatorPage;
import org.fluttercode.spigot.samples.wicket.dataprovider.JpaDataProviderPage;
import org.fluttercode.spigot.samples.wicket.dataprovider.SqlDataProviderPage;
import org.fluttercode.spigot.samples.wicket.search.JpaSearchPage;
import org.fluttercode.spigot.samples.wicket.search.SqlSearchPage;

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
