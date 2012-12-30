package org.easyGoingCrawler.docWriter;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Bloger 
{
	String id = null;
	String host = null;
	String name = null;
	String url = null;
	Set<String> articles = null;
	int articleAmt = 0; 
	int visit = 0;
	Date crawledDate = new Date();
	
	int magicNum = -1;
	
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
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public Set<String> getArticles()
	{
		return articles;
	}
	public void setArticles(Set<String> articles)
	{
		this.articles = articles;
	}
	public int getArticleAmt()
	{
		return articleAmt;
	}
	public void setArticleAmt(int articleAmt)
	{
		this.articleAmt = articleAmt;
	}
	public int getVisit()
	{
		return visit;
	}
	public void setVisit(int visit)
	{
		this.visit = visit;
	}
}
