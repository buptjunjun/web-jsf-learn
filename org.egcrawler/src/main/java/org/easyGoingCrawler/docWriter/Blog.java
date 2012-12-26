package org.easyGoingCrawler.docWriter;

import java.util.List;

public class Blog 
{

	String id = null;
	String host = null;
	String url = null;           // url
	byte[] content = null;       // content of the bolg 
	String encode = "utf8";      // encode of the bolg content
	String blogerURL = "";       // the author's url
	int comment = 0;			 // how many comments the blog has been earnt
	int visit = 0;				 // how many times the blog has been visited
	List<String> tags = null; 		 // tags of pictures
	int pictures = 0;			 // how many pictues in the blog's content
	
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
	public String getBlogerURL()
	{
		return blogerURL;
	}
	public void setBlogerURL(String blogerURL)
	{
		this.blogerURL = blogerURL;
	}
	public int getComment()
	{
		return comment;
	}
	public void setComment(int comment)
	{
		this.comment = comment;
	}
	public int getVisit()
	{
		return visit;
	}
	public void setVisit(int visit)
	{
		this.visit = visit;
	}
	public List<String> getTags()
	{
		return tags;
	}
	public void setTags(List<String>  tags)
	{
		this.tags = tags;
	}
	public int getPictures()
	{
		return pictures;
	}
	public void setPictures(int pictures)
	{
		this.pictures = pictures;
	}
}
