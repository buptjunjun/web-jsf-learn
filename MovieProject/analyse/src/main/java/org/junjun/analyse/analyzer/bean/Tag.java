package org.junjun.analyse.analyzer.bean;

public class Tag
{
	private String id="";           
	private String tagName="";   
	private String url="";	
	private int magicNum = -1;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTagName()
	{
		return tagName;
	}
	public void setTagName(String tagName)
	{
		this.tagName = tagName;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public int getMagicNum()
	{
		return magicNum;
	}
	public void setMagicNum(int magicNum)
	{
		this.magicNum = magicNum;
	}
	
}
