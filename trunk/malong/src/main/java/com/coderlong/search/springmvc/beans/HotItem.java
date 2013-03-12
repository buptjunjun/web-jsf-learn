package com.coderlong.search.springmvc.beans;

public class HotItem
{

	private String name = null;
	private String discription = null;
	public HotItem(String name , String discription )
	{
		this.name = name;
		this.discription = discription;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDiscription()
	{
		return discription;
	}
	public void setDiscription(String discription)
	{
		this.discription = discription;
	}
}
