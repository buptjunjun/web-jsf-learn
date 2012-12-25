package org.easyGoingCrawler.urlscheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.URLScheduler;
import org.easyGoingCrawler.urlStore.*;
import org.easyGoingCrawler.util.Localizer;

import com.mysql.jdbc.StringUtils;

public class RotateHostURLScheduler extends URLScheduler 
{
	private Logger logger = Logger.getLogger(RotateHostURLScheduler.class);
	private Queue<CrawlURI> urlQueue = new LinkedBlockingQueue<CrawlURI>(); 
	private EGDAO egdao = null;
	public static String UNKNOW_HOST = "unknow"; 
	public RotateHostURLScheduler() 
	{
		init();
	}
	
	
	
	@Override
	public synchronized CrawlURI get() 
	{
		
	}
	
	@Override
	public synchronized void put(CrawlURI curl) 
	{
		if( curl == null)
			return;
		
		curl.setContent(null);
		curl.setLastCrawlDate(new Date());
		this.urlstore.saveOrUpdate(curl);
		System.out.println(Thread.currentThread().getName()+" put curl: "+ curl.toString());
		// the urls extract from content
		List<String> urls = curl.getIncludeURLs();
		if(urls != null)
		{
			for(String url :urls)
			{
				CrawlURI u = new CrawlURI(url);
				URI uri = null;
				try
				{
					uri = new URI(url);
					u.setHost(uri.getHost());
				}
				catch(Exception e)
				{
					u.setHost(UNKNOW_HOST);
				}
				
				if (!this.urlstore.queryIfExist(u.getUrl()))
					this.urlstore.save(u);
				//System.out.println(Thread.currentThread().getName()+" put curl: "+ curl.toString());
			}
		}
		
		
		
	}
	
	public void select()
	{
		
		
	}
	public void putSeedsToDB()
	{
		if(this.hosts == null)
			return;
		
		for(int i = 0; i<this.hosts.size(); i++)
		{
			String url = hosts.get(i);
			CrawlURI u = new CrawlURI(url);
			URI uri = null;
			try
			{
				uri = new URI(url);
				u.setHost(uri.getHost());
			}
			catch(Exception e)
			{
				u.setHost(UNKNOW_HOST);
			}
			this.urlstore.saveOrUpdate(u);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		RotateHostURLScheduler ru = new RotateHostURLScheduler();
		ru.putSeedsToDB();
		
		/*for(int i = 0; i < 20 ;i++)
		{
			CrawlURI curl = ru.get();
			if(curl != null)
			System.out.println(curl.getUrl());
		}*/
	}

}
