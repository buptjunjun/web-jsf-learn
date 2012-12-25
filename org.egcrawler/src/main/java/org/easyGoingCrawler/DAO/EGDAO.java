package org.easyGoingCrawler.DAO;

import java.util.List;

import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
public interface EGDAO 
{
	public boolean insert (Object uri);
	
	List<CrawlURI> get();
	
	public boolean updateURL (Url url);
}
