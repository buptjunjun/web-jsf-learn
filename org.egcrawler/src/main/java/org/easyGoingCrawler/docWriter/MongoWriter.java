package org.easyGoingCrawler.docWriter;

import java.net.InetAddress;
import java.net.URI;

import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.analyzer.Analyzer;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;

public class MongoWriter extends DocWriter
{
	private EGDAOMongo daomongo = null; 

	public MongoWriter(EGDAOMongo daomongo)
	{
		this.daomongo = daomongo;
	}
	
	@Override
	public void write(CrawlURI curl)
	{
//		String host = this.getHost(curl.getUrl());
//		
//		if(host == null)
//		{
//			curl.setStatus(CrawlURI.STATUS_WRITE_ERROR);
//			return;
//		}
		
		
	}
	
	
	
	public String getHost(String url)
	{
		if (url == null)
			return null;
		
		try
		{
		URI uri =  URI.create(url);
		return uri.getHost();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
