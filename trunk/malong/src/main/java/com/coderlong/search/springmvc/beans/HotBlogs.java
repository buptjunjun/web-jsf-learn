package com.coderlong.search.springmvc.beans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.cb.data.Blog;

public class HotBlogs
{

	private static List<Blog> hotblogs= null;
	private Map<String ,Blog> mapblgs = new HashMap<String,Blog>();
	private int maxblog = 20;
	public HotBlogs()
	{	
		hotblogs = new ArrayList<Blog>();		
	}


	

	synchronized public void addItem(Blog item)
	{
		
		if(!this.mapblgs.containsKey(item.getId()))
		{
			hotblogs.add(item);
			mapblgs.put(item.getId(), item);
		}
		if(this.hotblogs.size() > maxblog)
		{
			Blog b = this.hotblogs.get(0);
			this.remove(b);
			mapblgs.remove(b.getId());
		}
	}
	
	public synchronized  void remove(Blog item)
	{
		hotblogs.remove(item);
	}
	
	public synchronized  void remove(int  location)
	{
		if(location >= 0 && location < hotblogs.size())
			hotblogs.remove(location);
	}

	public  List<Blog> gethotblogs()
	{
		
		return hotblogs;
	}
	
	public int size()
	{
		return hotblogs.size();
	}
		
	public int getMaxblog()
	{
		return maxblog;
	}

	public void setMaxblog(int maxblog)
	{
		this.maxblog = maxblog;
	}
}
