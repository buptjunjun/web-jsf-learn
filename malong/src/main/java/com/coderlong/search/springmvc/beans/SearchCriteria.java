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
	
	@Override
	public boolean equals(Object arg0)
	{
		// TODO Auto-generated method stub
		if(arg0 == null || !SearchCriteria.class.isInstance(arg0)) return false;
		SearchCriteria s = (SearchCriteria)arg0;
		if(this.query == null) return this.query == s.getQuery();
		else {
			return this.query.equals(s.getQuery());
		}
	}
}
