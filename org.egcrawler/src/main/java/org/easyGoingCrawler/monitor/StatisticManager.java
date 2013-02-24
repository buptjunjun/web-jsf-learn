package org.easyGoingCrawler.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Url;

public class StatisticManager
{
	private Map<String,Queue<Url>> urlMapok = new HashMap<String,Queue<Url>>(); 
	private Map<String,Queue<Url>> urlMapfail = new HashMap<String,Queue<Url>>(); 	
	private Map<String,Queue<Url>> urlMapNew = new HashMap<String,Queue<Url>>(); 
	
	private Map<String,Queue<Blog>> blogMapok = new HashMap<String,Queue<Blog>>(); 
	private Map<String,Queue<Blog>> blogMapfail = new HashMap<String,Queue<Blog>>(); 
	
	private int urlFetchedCount = 0;
	private int urlNewCount = 0;
	private int blogInsertCount = 0;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	
	public Map<String, Queue<Url>> getUrlMapok()
	{
		return urlMapok;
	}

	public void setUrlMapok(Map<String, Queue<Url>> urlMapok)
	{
		this.urlMapok = urlMapok;
	}

	public Map<String, Queue<Url>> getUrlMapfail()
	{
		return urlMapfail;
	}

	public void setUrlMapfail(Map<String, Queue<Url>> urlMapfail)
	{
		this.urlMapfail = urlMapfail;
	}

	public Map<String, Queue<Url>> getUrlMapNew()
	{
		return urlMapNew;
	}

	public void setUrlMapNew(Map<String, Queue<Url>> urlMapNew)
	{
		this.urlMapNew = urlMapNew;
	}

	public Map<String, Queue<Blog>> getBlogMapok()
	{
		return blogMapok;
	}

	public void setBlogMapok(Map<String, Queue<Blog>> blogMapok)
	{
		this.blogMapok = blogMapok;
	}

	public Map<String, Queue<Blog>> getBlogMapfail()
	{
		return blogMapfail;
	}

	public void setBlogMapfail(Map<String, Queue<Blog>> blogMapfail)
	{
		this.blogMapfail = blogMapfail;
	}
}
