package org.easyGoingCrawler.bean;

import java.util.Map;

public class BPerson
{
	
	private String id=null;
	private String name = null;  //������ 
	private	String alt = null;  //Ӱ����ĿURL 
	private Map<String,String> avatars = null;  //Ӱ��ͷ��
	
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
