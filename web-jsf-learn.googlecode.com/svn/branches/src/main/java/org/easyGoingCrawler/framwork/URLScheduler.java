package org.easyGoingCrawler.framwork;

/**
 * schedule the url for fetcher and   
 * @author Andy  weibobee@gmail.com 2012-9-22 
 *
 */
public abstract class  URLScheduler 
{
	public synchronized CrawlURI get(){return null;};
	public synchronized CrawlURI get(String key){return null;};
	public synchronized void put(CrawlURI url){};
}
