package org.easyGoingCrawler.common;

import java.util.Date;

/**
 * this class represent one file which has been fetched , the file can be a html or javascript file or etc.
 * @author andyWebsense
 *
 */
public class FetchedFile
{
	// the url of this file
	private String url=null;
	
	// content of this file 
	private byte[] content=null;
	
	// encode of this file  ,default is utf-8
	private String encode = "utf-8";
	
	// time when the file fetched
	private Date   time = null;
	
	public FetchedFile(String url,byte[] content,String encode ,Date   time  )
	{
		this.content=content;
		this.encode = encode;
		this.encode=encode;
		this.time = time;
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
	public Date getTime()
	{
		return time;
	}
	public void setTime(Date time)
	{
		this.time = time;
	}
}
