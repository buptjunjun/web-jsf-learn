package org.easyGoingCrawler.urlscheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.URLScheduler;
import org.easyGoingCrawler.util.Localizer;

import com.mysql.jdbc.StringUtils;

public class RotateHostURLScheduler extends URLScheduler 
{
	private Queue<CrawlURI> urlQueue = new LinkedBlockingQueue<CrawlURI>(); 
	private EGDAO egdao = null;
	public static String UNKNOW_HOST = "unknow"; 
	public RotateHostURLScheduler(EGDAO egdao) 
	{
		this.egdao= egdao;
		this.urlQueue.addAll(this.egdao.get());
	}
	
	
	
	@Override
	public synchronized CrawlURI get() 
	{
		if(this.urlQueue.size() <= 20)
		{
			this.urlQueue.addAll(this.egdao.get());
		}
		if(this.urlQueue != null && this.urlQueue.size() > 0)
			return this.urlQueue.poll();
		
		return null;
		
	}
	
	@Override
	public synchronized void put(CrawlURI curl) 
	{
		if( curl == null)
			return;
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		/*for(int i = 0; i < 20 ;i++)
		{
			CrawlURI curl = ru.get();
			if(curl != null)
			System.out.println(curl.getUrl());
		}*/
	}

}
