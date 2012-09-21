package org.easyGoingCrawler.urlStore;

import java.util.Date;


/**
 * the URL class 
 * 
 * @author Andy  weibobee@gmail.com 2012-9-15
 *
 */
public class URLInfo
{	
	public String url = null;   
	
	/**
	 * status = -1: url is crawled but failed
	 * status = 0: url is not crawled
	 * status = 1: url is crawled 
	 */
	public int status = 0; 
	
	String encode   = "utf-8";
    
    /**
     * the time one url was collected
     */
	public String collectTime = new Date().toLocaleString();
    
    /**
     * the time one url last crawled
     */
	public String lastCrawlTime = new Date().toLocaleString();
    
    public URLInfo() 
    {
		// TODO Auto-generated constructor stub
	}
    public URLInfo(String url,int status,String encode, String collectTime,String lastCrawlTime) 
    {
		// TODO Auto-generated constructor stub
    	this.url = url;
    	this.status = status;
    	this.encode = encode;
    	this.collectTime = collectTime;
    	this.lastCrawlTime = lastCrawlTime;
	}
    
    
    public String getUrl()
    {
	return url;
     }
	public void setUrl(String url) 
	{
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getCollectTime() 
	{
		return collectTime;
	}
	public void setCollecttime(String collectTime) 
	{
		this.collectTime = collectTime;
	}
	public String getLastCrawlTime() 
	{
		return lastCrawlTime;
	}
	public void setLastCrawlTime(String lastCrawlTime) 
	{
		this.lastCrawlTime = lastCrawlTime;
	}
}