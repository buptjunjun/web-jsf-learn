package org.easyGoingCrawler.docWriter;

import java.util.Date;

import org.easyGoingCrawler.framwork.CrawlURI;

public class Url
{
	public static final int  CRAWLED = 1;
	public static final int  UNCRAWLED = 0;
	

	private String id = null;
	private String host = null;
	private String url = null;
	private Date lastCrawled = new Date();
	private int failedTime = 0;               // how many times the crawling has failed	
	private int flag = 0;
	
	public Date getLastCrawled()
	{
		return lastCrawled;
	}
	public void setLastCrawled(Date lastCrawled)
	{
		this.lastCrawled = lastCrawled;
	}
	public int getFailedTime()
	{
		return failedTime;
	}
	public void setFailedTime(int failedTime)
	{
		this.failedTime = failedTime;
	}
	public int getFlag()
	{
		return flag;
	}
	public void setFlag(int flag)
	{
		this.flag = flag;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getHost()
	{
		return host;
	}
	public void setHost(String host)
	{
		this.host = host;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public CrawlURI toCrawlURI()
	{
		CrawlURI uri = new CrawlURI();
		
		uri.setUrl(url);
		uri.setHost(host);
		uri.setStatus(CrawlURI.STATUS_OK);
		
		return uri;
	}
}
