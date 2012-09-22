package org.easyGoingCrawler.framwork;

import java.util.Date;
import java.util.List;

/**
 * this class represent one file which has been fetched , the file can be a html or javascript file or etc.
 * @author andyWebsense
 *
 */
public class CrawlURI
{	
	// the url  to fetch
	private String url=null;
	
	// content of this file 
	private byte[] content=null;
	
	// encode of this file  ,default is utf-8
	private String encode = "utf-8";
	
	// http status returned from server
	private int httpstatus= -1;

	// time when the url are collected into the database
	private Date   collectDate = null;
	
	// time when the url are recently crawled 
	private Date   lastCrawlDate = null;
	
	// max size of content in byte default is 1M 
	private long maxContentSize = 1024*1024;
	
	// tag of this file
	private String tags = "";
	
	// urls that this CrawlURL contains 
	private List<String> includeURLs;

	// status of last processor 
	private boolean status  =  false;

	// path of file on disk
	String path = null;
	
	public CrawlURI(String url )
	{
		this.url = url;
	}
	
	
	public byte[] getContent()
	{
		return content;
	}
	public void setContent(byte[] content)
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


	public long getMaxContentSize() {
		return maxContentSize;
	}


	public void setMaxContentSize(long maxContentSize) {
		this.maxContentSize = maxContentSize;
	}

	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<String> getIncludeURLs() {
		return includeURLs;
	}


	public void setIncludeURLs(List<String> includeURLs) {
		this.includeURLs = includeURLs;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}



}
