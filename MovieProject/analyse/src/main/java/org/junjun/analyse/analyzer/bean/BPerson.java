package org.junjun.analyse.analyzer.bean;

import java.util.List;
import java.util.Map;

public class BPerson
{

	private String id=null;
	private String name = null;  //中文名 
	private	String alt = null;  //影人条目URL 
	private List<String> pictures = null;  //影人头像
	private List<String> movies = null;   //相关的电影
	
	
	public List<String> getPictures()
	{
		return pictures;
	}
	public void setPictures(List<String> pictures)
	{
		this.pictures = pictures;
	}
	public List<String> getMovies()
	{
		return movies;
	}
	public void setMovies(List<String> movies)
	{
		this.movies = movies;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAlt()
	{
		return alt;
	}
	public void setAlt(String alt)
	{
		this.alt = alt;
	}

}
