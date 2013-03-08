package com.coderlong.search.springmvc.beans;

import java.util.Date;
import java.util.List;

public class SearchResult
{

	private String title = "";
	private String content = "";
	private String url = "";
	private List<String> tags = null;
	private Date  date = null;
	private Object reserve = null;
	public Object getReserve()
	{
		return reserve;
	}

	public void setReserve(Object reserve)
	{
		this.reserve = reserve;
	}

	public SearchResult(String title , String content, String url ,List<String> tags ,Date  date )
	{
		this.title = title;
		this.content = content;
		this.url = url;
		this.tags = null;
		this.date = new Date();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}


	public List<String> getTags()
	{
		return tags;
	}

	public void setTags(List<String> tags)
	{
		this.tags = tags;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}


	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
