package org.junjun.analyse.analyzer.bean;

import java.util.List;

public class BRelatedMovies
{
	private String id = null;   //movieid
	private List<String> urls = null; // urls of related movies;
	
	
	
	public List<String> getUrls()
	{
		return urls;
	}



	public void setUrls(List<String> urls)
	{
		this.urls = urls;
	}



	public String getId()
	{
		return id;
	}



	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return this.getId()+":"+this.getUrls();
	}

}
