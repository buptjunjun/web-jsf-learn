package org.search.beans;

import java.util.Date;
import java.util.List;

public class ResultItemBean
{

	private String title = "";
	private String content = "";
	private String url = "";
	private List<String> tags = null;
	private Date  date = null;
	private String id = null;
	
	public ResultItemBean(	 String title ,
							 String content,
							 String url ,
							 List<String> tags ,
							 Date  date ,String id )
	{
		this.title = title;
		this.content = content;
		this.url = url;
		this.tags = null;
		this.date = new Date();
		this.id = id;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
