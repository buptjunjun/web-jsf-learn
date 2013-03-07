package com.coderlong.search.springmvc.beans;

public class SearchCriteria
{
	private String query = null;
	
	public SearchCriteria()
	{
		// TODO Auto-generated constructor stub
	}
	
	public SearchCriteria(String query)
	{
		this.query = query;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}
}
