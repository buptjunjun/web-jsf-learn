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
	private URLAnalyzer urAnalyzer = null;
	private String host = "";
	
	public String getHost()
	{
		return host;
	}


	public void setHost(String host)
	{
		this.host = host;
	}


	public RotateHostURLScheduler(EGDAO egdao,String host,URLAnalyzer urAnalyzer ) 
	{
		this.urAnalyzer = urAnalyzer; 
		this.host = host;
		this.egdao= egdao;
	}
	
	
	@Override
	public synchronized CrawlURI get() 
	{
		if(this.urlQueue.size() <= 0)
		{
			List l = this.egdao.get(this.host);
			this.urlQueue.addAll(l);
			
		}
		
		if(this.urlQueue != null && this.urlQueue.size() > 0)
		{
			CrawlURI curl = this.urlQueue.poll();
			loger.info("@@RotateHostURLScheduler: get a curl:"+curl);
			return curl;
		}
		
		return null;
		
	}
	
	@Override
	public synchronized CrawlURI get(String key)
	{
		// TODO Auto-generated method stub
		
		return null;
	}
	
	@Override
	public synchronized void put(CrawlURI curl) 
	{
		System.out.println(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler :put a curl: " +curl.toString());
		Url url = Converter.CrawlURI2Url(curl);
		if(url!=null)
		{
			if(curl.getHttpstatus() == 200)
				url.setFlag(Url.CRAWLED);
			else
				url.setFlag(Url.UNCRAWLED);
			
			egdao.updateURL(url);
			loger.info(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler: update a url:"+url);
		}
		else 
		{
			System.out.println(" in put : curl == null");
			return;
		}
		
		List<String> subUrls = curl.getIncludeURLs();
		if(subUrls == null ) return ;
		
		int i = 0;
		// save crawled urls 
		for(String u: subUrls)
		{
			int status =urAnalyzer.analyze(curl.getHost(), u);
			if(status== URLAnalyzer.DELETE)
			{
				//System.out.println("delete == " +u);
				continue;
			}
			
			
			Url tmpurl = new Url();		
			tmpurl.setUrl(u);
			tmpurl.setId(Converter.urlEncode(u));
			tmpurl.setFlag(Url.UNCRAWLED);
			tmpurl.setHost(curl.getHost());
			tmpurl.setLastCrawled(new Date());	
			tmpurl.setType(status);
			if(i++ % 30 == 0)
			{
				System.out.println(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler: insert  a url:"+tmpurl);
				loger.info(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler: insert  a url:"+tmpurl);
			}
			egdao.insert(tmpurl);
		}
	}
	
	public void insertSeeds()
	{
		Url tmpurl = new Url();	
//		
//		tmpurl.setUrl("http://www.ibm.com/developerworks/cn/");
//		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
//		tmpurl.setFlag(Url.UNCRAWLED);
//		tmpurl.setHost("ibm.cn");
//		tmpurl.setLastCrawled(new Date());
//		tmpurl.setType(Url.URL_OTHER);
//		this.egdao.insert(tmpurl);
	
		tmpurl.setUrl("http://www.iteye.com/blogs");
		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
		tmpurl.setFlag(Url.UNCRAWLED);
		tmpurl.setHost("www.iteye.com/blogs");
		tmpurl.setLastCrawled(new Date());
		tmpurl.setType(Url.URL_OTHER);
		this.egdao.insert(tmpurl);
		
//		tmpurl.setUrl("http://blog.csdn.net/web/index.html");
//		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
//		tmpurl.setFlag(Url.UNCRAWLED);
//		tmpurl.setHost("blog.csdn.net");
//		tmpurl.setLastCrawled(new Date());
//		tmpurl.setType(Url.URL_OTHER);
//		this.egdao.insert(tmpurl);
		
//		tmpurl.setUrl("http://www.cnblogs.com/AllBloggers.aspx");
//		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
//		tmpurl.setFlag(Url.UNCRAWLED);
//		tmpurl.setHost("www.cnblogs.com");
//		tmpurl.setLastCrawled(new Date());
//		tmpurl.setType(Url.URL_OTHER);
//		this.egdao.insert(tmpurl);
		
//		tmpurl.setUrl("http://blog.chinaunix.net/");
//		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
//		tmpurl.setFlag(Url.UNCRAWLED);
//		tmpurl.setHost("blog.chinaunix.net");
//		tmpurl.setLastCrawled(new Date());
//		tmpurl.setType(Url.URL_OTHER);
//		this.egdao.insert(tmpurl);
//		
//		tmpurl.setUrl("http://www.oschina.net/blog/");
//		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
//		tmpurl.setFlag(Url.UNCRAWLED);
//		tmpurl.setHost("my.oschina.net");
//		tmpurl.setLastCrawled(new Date());
//		tmpurl.setType(Url.URL_OTHER);
//		this.egdao.insert(tmpurl);
//		
//		tmpurl.setUrl("http://blog.51cto.com/");
//		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
//		tmpurl.setFlag(Url.UNCRAWLED);
//		tmpurl.setHost("blog.51cto.com");
//		tmpurl.setLastCrawled(new Date());
//		tmpurl.setType(Url.URL_OTHER);
//		this.egdao.insert(tmpurl);
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

}
