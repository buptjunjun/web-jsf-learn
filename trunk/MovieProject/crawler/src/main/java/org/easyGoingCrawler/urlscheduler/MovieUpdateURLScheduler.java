package org.easyGoingCrawler.urlscheduler;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.URLScheduler;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.URLAnalyzer;

public class MovieUpdateURLScheduler extends URLScheduler 
{
	Logger loger =  Logger.getLogger(MovieUpdateURLScheduler.class);
	
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


	public MovieUpdateURLScheduler(EGDAO egdao,String host,URLAnalyzer urAnalyzer ) 
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
			loger.info(" get a curl:"+curl);
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
	public  void put(CrawlURI curl) 
	{
		
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
	
		tmpurl.setUrl("http://www.funshion.com/subject/relation_list/88663");
		tmpurl.setId(Converter.urlEncode(tmpurl.getUrl()));
		tmpurl.setFlag(Url.UNCRAWLED);
		tmpurl.setHost("www.funshion.com");
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
