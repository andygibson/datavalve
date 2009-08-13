package org.jdataset.sample.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.jdataset.sample.wicket.custom.FileCustomPaginatorPage;
import org.jdataset.sample.wicket.custom.JpaCustomPaginatorPage;
import org.jdataset.sample.wicket.custom.SqlCustomPaginatorPage;
import org.jdataset.sample.wicket.dataprovider.JpaDataProviderPage;
import org.jdataset.sample.wicket.dataprovider.SqlDataProviderPage;
import org.jdataset.sample.wicket.search.JpaSearchPage;
import org.jdataset.sample.wicket.search.SqlSearchPage;

public class HeaderLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	public HeaderLinkPanel(String id) {
		super(id);
				
		add(new BookmarkablePageLink<WebPage>("jpaDatasetLink",JpaCustomPaginatorPage.class));
		add(new BookmarkablePageLink<WebPage>("sqlDatasetLink",SqlCustomPaginatorPage.class));
		add(new BookmarkablePageLink<WebPage>("fileDatasetLink",FileCustomPaginatorPage.class));
		add(new BookmarkablePageLink<WebPage>("jpaProviderLink",JpaDataProviderPage.class));
		add(new BookmarkablePageLink<WebPage>("sqlProviderLink",SqlDataProviderPage.class));
		
		add(new BookmarkablePageLink<WebPage>("jpaSearchLink",JpaSearchPage.class));
		add(new BookmarkablePageLink<WebPage>("sqlSearchLink",SqlSearchPage.class));
		
	}
	
	

	

}
