package org.easyGoingCrawler.common;

import java.util.Date;

/**
 * this class represent one file which has been fetched , the file can be a html or javascript file etc.
 * @author andyWebsense
 *
 */
public class FetchedFile
{

	private String content=null;
	private String encode = "utf-8";
	private String url=null;
	private Date   time = null;
	
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
	public Date getTime()
	{
		return time;
	}
	public void setTime(Date time)
	{
		this.time = time;
	}
}
