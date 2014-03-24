package org.junjun.spring.charpter5.common;

public class Spitter
{	
	private long id =-1l;
	private String username = null;
	private String password = null;
	private String fullname = null;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getFullname()
	{
		return fullname;
	}
	public void setFullname(String fullname)
	{
		this.fullname = fullname;
	}
	
	@Override
	public String toString()
	{
		return this.id+","+this.username+","+this.password+","+this.fullname;
	}
	

}
