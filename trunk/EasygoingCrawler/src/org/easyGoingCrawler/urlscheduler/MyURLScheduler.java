package org.easyGoingCrawler.urlscheduler;

import java.util.List;
import java.util.Queue;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.URLScheduler;
import org.easyGoingCrawler.urlStore.*;
public class MyURLScheduler extends URLScheduler 
{
	private Queue<CrawlURI> urlQueue = new LinkedBlockingQueue<CrawlURI>(); 
	private MysqlDB mysql = null;
	private int miniSize = 10;
	private int maxSize = 100;
	private String getRegex = "";
	
	public MyURLScheduler() 
	{
		mysql = new MysqlDB("urldb");
	}
	
	@Override
	public synchronized CrawlURI get() 
	{
		// if the url cache is null 
		if(this.urlQueue.size() <= this.miniSize)
		{
			
		}
		return this.urlQueue.poll();
	}
	
	@Override
	public synchronized void put(CrawlURI curl) 
	{
		if( curl == null)
			return;
		
		curl.setContent(null);
		if (curl.getHttpstatus() != 200)
		{
			
		}
		else
		{
			
		}
		
		// the urls extract from content
		List<String> urls = curl.getIncludeURLs();
		
		if(urls != null)
		{
			for(String url :urls)
			{
				
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

	}

}
