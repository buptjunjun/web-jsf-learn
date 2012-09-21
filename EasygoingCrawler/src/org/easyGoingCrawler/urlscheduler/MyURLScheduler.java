package org.easyGoingCrawler.urlscheduler;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.URLScheduler;

public class MyURLScheduler extends URLScheduler 
{

	@Override
	public synchronized CrawlURI get() 
	{
		// TODO Auto-generated method stub
		return super.get();
	}
	
	@Override
	public synchronized void put(CrawlURI url) 
	{
		// TODO Auto-generated method stub
		super.put(url);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
