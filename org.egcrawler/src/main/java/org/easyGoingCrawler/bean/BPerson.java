package org.easyGoingCrawler.bean;

import java.util.Map;

public class BPerson
{
	
	private String id=null;
	private String name = null;  //中文名 
	private	String alt = null;  //影人条目URL 
	private Map<String,String> avatars = null;  //影人头像
	
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
	public Map getAvatars()
	{
		return avatars;
	}
	public void setAvatars(Map avatars)
	{
		this.avatars = avatars;
	}
}
