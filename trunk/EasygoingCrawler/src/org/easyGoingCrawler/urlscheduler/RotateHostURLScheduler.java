package org.easyGoingCrawler.urlscheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
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
	private int miniSize = 10;
	private int maxSize = 100;
	private String getRegex = "";
	private URLStoreH4 urlstore = null;
	private List<String> hosts = null;
	
	public RotateHostURLScheduler() 
	{
		urlstore = new URLStoreH4();
		hosts = new ArrayList<String>();
		init();
	}
	
	
	private void init()
	{
	   String seedsFile = Localizer.getMessage("seeds");
	   try 
	   {
		BufferedReader bf = new BufferedReader(new FileReader(seedsFile));
		String line = null;
		while((line = bf.readLine()) != null)
		{
			if(StringUtils.isNullOrEmpty(line))
				continue;
			String host = line.trim();
			this.hosts.add(host);
		}		
	   }
	   catch (Exception e) 
	   {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error("read seeds file error :" + e.getMessage());
	   }
	}
	
	
	@Override
	public synchronized CrawlURI get() 
	{
	// if the url cache is null 
		if(this.urlQueue.size() <= this.miniSize)
		{
			List [] listCache = new List[hosts.size()];
			
			for(int i = 0; i < this.hosts.size(); i++)
			{
				String host = hosts.get(i);
				String query = "from CrawlURI where url like '%"+host+"%'" 
							+  "  and httpstatus = -1"
							+  " order by lastCrawlDate  asc";	
				
				List l = urlstore.query(query);
				listCache[i] = l;
			}
			
			while(true)
			{
				boolean flag = false;
				for(int j = 0; j<listCache.length; j++)
				{
					List l = listCache[j];
					if(l == null || l.size() == 0)
						continue;
					CrawlURI c = (CrawlURI) l.get(0);
					this.urlQueue.add(c);
					l.remove(c);
					flag = true;
				}
				
				if(flag == false)
					break;
			}
		}
		CrawlURI curl  = urlQueue.poll();
		System.out.println(Thread.currentThread().getName()+" get curl: "+ curl.toString());
		return curl;
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
				this.urlstore.save(new CrawlURI(url));
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
			this.urlstore.saveOrUpdate(new CrawlURI(hosts.get(i)));
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		RotateHostURLScheduler ru = new RotateHostURLScheduler();
		//ru.putSeedsToDB();
		
		for(int i = 0; i < 20 ;i++)
		{
			CrawlURI curl = ru.get();
			if(curl != null)
			System.out.println(curl.getUrl());
		}
	}

}
