package org.easyGoingCrawler.DAO;

import java.util.List;

import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
public interface EGDAO 
{
	public boolean insert (Object uri);
	
	List<CrawlURI> get();
	List<CrawlURI> get(String key);
	
	public boolean updateURL (Url url);
}
