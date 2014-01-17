package byr.crawler.urlscheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;


import byr.crawler.framework.Config;
import byr.crawler.framework.CrawlURI;
import byr.crawler.framework.URLScheduler;
import byr.crawler.framework.Url;
import byr.crawler.persist.DAOMongo;
import byr.crawler.utils.CommonUtils;


public class RotateHostURLScheduler extends URLScheduler 
{
	Logger loger =  Logger.getLogger(RotateHostURLScheduler.class);
	
	private Queue<CrawlURI> urlQueue = new LinkedBlockingQueue<CrawlURI>(); 
	private DAOMongo dao = null;
	public static String UNKNOW_HOST = "unknow"; 
	private String host = "";
	private String start_url = null;
	
	public RotateHostURLScheduler(String host,String start_url,String dbName) {
		this.host = host;
		this.start_url = start_url;
		this.dao = new DAOMongo(dbName);
		initUrl();
	}
	
	public RotateHostURLScheduler(String host,String start_url,String dbhost , int port , String dbName) {
		this.host = host;
		this.start_url = start_url;
		this.dao = new DAOMongo(dbhost , port ,dbName);
		initUrl();
	}
	
	
	@Override
	public synchronized CrawlURI get() 
	{
		if(this.urlQueue.size() <= 0)
		{
			List l = this.queryByhost(this.host,10);
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
	public synchronized void put(CrawlURI curl) 
	{
		System.out.println(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler :put a curl: " +curl.toString());
		Url url = curl.getUrl();
		if(url!=null)
		{
			if(curl.getHttpstatus() == 200 && url.getType()!=Url.URL_REPEAT)
				url.setFlag(Url.CRAWLED);
			else
				url.setFlag(Url.UNCRAWLED);
			
			this.updateUrl(url);
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
			int status = Config.classifyUrl(u);
			if(status == Url.URL_DELETE)
				continue;
			
			Url tmpurl = new Url();		
			tmpurl.setUrl(u);
			tmpurl.setId(CommonUtils.urlEncode(u));
			tmpurl.setFlag(Url.UNCRAWLED);
			tmpurl.setHost(curl.getHost());
			tmpurl.setLastCrawled(new Date());	
			
			if(i++ % 30 == 0)
			{
				System.out.println(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler: insert  a url:"+tmpurl);
				loger.info(Thread.currentThread().getName()+"-"+ "@@RotateHostURLScheduler: insert  a url:"+tmpurl);
			}
			dao.insert(tmpurl);
		}
	}

	public String getHost()
	{
		return host;
	}


	public void setHost(String host)
	{
		this.host = host;
	}
	
	
	public void initUrl()
	{
		Url tmpurl = new Url();	
	
		tmpurl.setUrl(this.start_url);
		tmpurl.setId(CommonUtils.urlEncode(this.start_url));
		tmpurl.setFlag(Url.UNCRAWLED);
		tmpurl.setHost(this.host);
		tmpurl.setLastCrawled(new Date());
		tmpurl.setType(Url.URL_REPEAT);

		this.dao.insert(tmpurl);
		
		Map map = new HashMap();
		map.put("id", tmpurl.getId());
		this.dao.update(tmpurl, map);
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
	
	
	private void updateUrl(Url url)
	{
		Map map = new HashMap<String,String>();
		map.put("id", url.getId());
		this.dao.update(url, map);
	}
	
	private void insert(Object obj)
	{
		this.dao.insert(obj);
	}
	
    
	
	public List<CrawlURI> queryByhost(String host, int limit)
	{
		List<CrawlURI> list = new ArrayList<CrawlURI>();
		List<Url> lurl = null;
		try
		{
			
			Map constrains = new HashMap();
			constrains.put("host", host);
			constrains.put("flag", Url.UNCRAWLED);
			// querying a blog's probability is  70% 
			if( new Random().nextInt(10) < 7)
			{
				lurl = this.dao.search(constrains, "lastCrawled", DAOMongo.ASCENDING, limit, Url.class);
			}
			else
			{
				constrains.put("type", Url.URL_REPEAT);
				lurl = this.dao.search(constrains, "lastCrawled", DAOMongo.ASCENDING, limit, Url.class);
			}
	        
			if(lurl !=null)
        	{
				for(Url url : lurl )
	        	{
	        		list.add(url.toCrawlURI());
	        	}
        	}
	        
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
    
	

}
