package org.easyGoingCrawler.framwork;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.easyGoingCrawler.docWriter.MirrorWriter;
import org.easyGoingCrawler.extractor.HTMLExtractor;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.util.Converter;

/**
 * this class represent one file which has been fetched , the file can be a html or javascript file or etc.
 * @author andyWebsense
 *
 */
public class CrawlURI implements Serializable
{	
	public final static int STATUS_OK = 0;
	public final static int STATUS_FETCH_ERROR = 1;
	public final static int STATUS_EXTRACT_ERROR = 2;
	public final static int STATUS_WRITE_ERROR = 2;
	
	// the url  to fetch
	private String url=null;
	
	private String host = null;

	// content of this file 
	private String content=null;
	
	// encode of this file  ,default is utf-8
	private String encode = "UTF-8";
	
	// http status returned from server
	private int httpstatus= -1;

	// time when the url are collected into the database
	private Date   collectDate = null;
	
	// time when the url are recently crawled 
	private Date   lastCrawlDate = null;

	// urls that this CrawlURL contains 
	private List<String> includeURLs;

	// status of last processor 
	private int status = CrawlURI.STATUS_OK;

	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public CrawlURI() 
	{
		// TODO Auto-generated constructor stub
	}
	public CrawlURI(String url )
	{
		this.url = url;
	}
	
	
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getEncode()
	{
		return encode;
	}
	public void setEncode(String encode)
	{
		this.encode = encode;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

	public Date getCollectDate() {
		return collectDate;
	}


	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}


	public Date getLastCrawlDate() {
		return lastCrawlDate;
	}


	public void setLastCrawlDate(Date lastCrawlDate) {
		this.lastCrawlDate = lastCrawlDate;
	}
	
	public int getHttpstatus() {
		return httpstatus;
	}


	public void setHttpstatus(int httpstatus) {
		this.httpstatus = httpstatus;
	}


	public List<String> getIncludeURLs() {
		return includeURLs;
	}


	public void setIncludeURLs(List<String> includeURLs) {
		this.includeURLs = includeURLs;
	}
	
	private Object reserve = null; 
	
	public Object getReserve()
	{
		return reserve;
	}
	public void setReserve(Object reserve)
	{
		this.reserve = reserve;
	}
	public static void main(String [] args)
	{
	/*	Extractor e = new HTMLExtractor();
		Fetcher f = new HttpFetcher();
		DocWriter w = new MirrorWriter();
	//	URLScheduler s = new MyURLScheduler();
		
		EGCrawler crawler = new EGCrawler();
		crawler.setDocWriter(w);
		crawler.setExtractor(e);
		crawler.setFetcher(f);
		crawler.setScheduler(s);
		
		crawler.startCrawl();
		crawler.start();*/
	}
	
	public String getHost()
	{
		return host;
	}
	public void setHost(String host)
	{
		this.host = host;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String ret = this.url +", httpstatus = " +this.getHttpstatus() 
				+ " , status = " + this.status +", encode = " + this.encode
				+ " , path = " +", lastcrawlDate = " + this.getLastCrawlDate()+"id:"+Converter.urlEncode(this.url);
		return ret;
	}

}
