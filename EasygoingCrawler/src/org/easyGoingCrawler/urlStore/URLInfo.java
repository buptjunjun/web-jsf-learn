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
	
	/**
	 *  the directory the file is stored
	 */
	public String path = "";
	
	/**
	 * status of http reponse;
	 */
	public int statusCode = -1;
	
	/**
	 *  encode of one url
	 */
	public String encode   = "utf-8";
    
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
    
}