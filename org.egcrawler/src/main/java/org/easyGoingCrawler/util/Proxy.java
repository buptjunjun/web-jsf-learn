package org.easyGoingCrawler.util;

import org.easyGoingCrawler.framwork.Fetcher;

public class Proxy
{
	private Fetcher f = null;
	private String ip = null;
	private int port = 8080;
	private String testUrl = null;
	
	public Proxy( Fetcher f , String ip , int port ,String testUrl )
	{
		this.f = f;
		this.ip = ip;
		this.port =port;
		this.testUrl = testUrl;
	}
	
	public boolean checkConnection()
	{
		return true;
	}
	
	public Fetcher getF()
	{
		return f;
	}

	public void setF(Fetcher f)
	{
		this.f = f;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getTestUrl()
	{
		return testUrl;
	}

	public void setTestUrl(String testUrl)
	{
		this.testUrl = testUrl;
	}

	@Override
	public String toString()
	{
	  return this.ip +":"+this.port+ " ";
	}
}