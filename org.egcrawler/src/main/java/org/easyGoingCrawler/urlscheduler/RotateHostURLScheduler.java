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
import org.easyGoingCrawler.analyzer.Analyzer;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.URLScheduler;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.Localizer;
import org.easyGoingCrawler.util.RandomList;
import org.easyGoingCrawler.util.URLAnalyzer;

public class RotateHostURLScheduler extends URLScheduler 
{
	Logger loger =  Logger.getLogger(RotateHostURLScheduler.class);
	
	private Queue<CrawlURI> urlQueue = new LinkedBlockingQueue<CrawlURI>(); 
	private EGDAO egdao = null;
	public static String UNKNOW_HOST = "unknow"; 
	
	private Analyzer<Blog> blogAnalyzer = null; 
	
	private Analyzer<Bloger> blogerAnalyzer = null;
	
	private URLAnalyzer urAnalyzer = null;

	public RotateHostURLScheduler(EGDAO egdao) 
	{
		this.egdao= egdao;
		this.urlQueue.addAll(this.egdao.get());
	}
	
	
	
	@Override
	public synchronized CrawlURI get() 
	{
		if(this.urlQueue.size() <= 20)
		{
			List l = this.egdao.get();
			this.urlQueue.addAll(l);
			
		}
		
		if(this.urlQueue != null && this.urlQueue.size() > 0)
			return this.urlQueue.poll();
		
		return null;
		
	}
	
	@Override
	public synchronized void put(CrawlURI curl) 
	{
		if( curl == null || curl.getHttpstatus()!=200)
		{
			System.out.println("== after fetcher and etract: "+curl);
			return;
		}
		System.out.println(" in put : " +curl.toString());
	
		if(urAnalyzer.analyze(curl.getHost(), curl.getUrl()) == urAnalyzer.SAVE)
		{
			// save a bolg or bloger.
			String host = curl.getHost();	
			Bloger bloger = this.blogerAnalyzer.analyze(host,curl.getEncode(),curl.getContent());
			Blog blog = this.blogAnalyzer.analyze(host,curl.getEncode(),curl.getContent());
					
			if(blog != null  && bloger != null)	
			{
				System.out.println("++ insert blog :"+blog);
				System.out.println("++ inster bloger :"+bloger);
				
				if(curl.getUrl() != null)
				{	
					blog.setUrl(curl.getUrl());
					blog.setId(Converter.urlEncode(curl.getUrl()));
					this.egdao.insert(blog);
			    	this.egdao.insert(bloger);
				}
			}				
		}
		
		
		Url url = Converter.CrawlURI2Url(curl);
		if(url!=null)
		{
			if(curl.getStatus() == CrawlURI.STATUS_OK)
				url.setFlag(Url.CRAWLED);
			else
				url.setFlag(Url.UNCRAWLED);
			
			egdao.updateURL(url);
		}
		
		List<String> subUrls = curl.getIncludeURLs();
		if(subUrls == null ) return ;
		
		// save crawled urls 
		for(String u: subUrls)
		{
			if(urAnalyzer.analyze(curl.getHost(), u) == URLAnalyzer.DELETE)
			{
				//System.out.println("delete == " +u);
				continue;
			}
			System.out.println("insert	 == " +u);
			Url tmpurl = new Url();		
			tmpurl.setUrl(u);
			tmpurl.setId(Converter.urlEncode(u));
			tmpurl.setFlag(Url.UNCRAWLED);
			tmpurl.setHost(curl.getHost());
			tmpurl.setLastCrawled(new Date());
			
			egdao.insert(tmpurl);
		}
	}
	
	public void insertSeeds()
	{
		Url tmpurl = new Url();		
		tmpurl.setUrl("http://blog.csdn.net/web/index.html");
		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
		tmpurl.setFlag(Url.UNCRAWLED);
		tmpurl.setHost("blog.csdn.net");
		tmpurl.setLastCrawled(new Date());
		this.egdao.insert(tmpurl);
		
		tmpurl.setUrl("http://www.cnblogs.com/index.html");
		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
		tmpurl.setFlag(Url.UNCRAWLED);
		tmpurl.setHost("www.cnblogs.com");
		tmpurl.setLastCrawled(new Date());
		this.egdao.insert(tmpurl);
		
		tmpurl.setUrl("http://blog.chinaunix.net/");
		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
		tmpurl.setFlag(Url.UNCRAWLED);
		tmpurl.setHost("blog.chinaunix.net");
		tmpurl.setLastCrawled(new Date());
		
		this.egdao.insert(tmpurl);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		/*for(int i = 0; i < 20 ;i++)
		{
			CrawlURI curl = ru.get();
			if(curl != null)
			System.out.println(curl.getUrl());
		}*/
		
	}
	
	
	public URLAnalyzer getUrAnalyzer()
	{
		return urAnalyzer;
	}



	public void setUrAnalyzer(URLAnalyzer urAnalyzer)
	{
		this.urAnalyzer = urAnalyzer;
	}
	
	public void setBlogAnalyzer(Analyzer<Blog> blogAnalyzer)
	{
		this.blogAnalyzer = blogAnalyzer;
	}



	public Analyzer<Bloger> getBlogerAnalyzer()
	{
		return blogerAnalyzer;
	}



	public void setBlogerAnalyzer(Analyzer<Bloger> blogerAnalyzer)
	{
		this.blogerAnalyzer = blogerAnalyzer;
	}

	public Analyzer<Blog> getBlogAnalyzer()
	{
		return blogAnalyzer;
	}



	


}
