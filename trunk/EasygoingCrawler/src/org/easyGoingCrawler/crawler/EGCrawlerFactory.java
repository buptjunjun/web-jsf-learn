package org.easyGoingCrawler.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.framwork.*;

/**
 *  EGCrawlerFactory create a EGCrawler according to configure file
 *  @author Andy  weibobee@gmail.com 2012-9-21
 *
 */
public class EGCrawlerFactory 
{
	private Logger logger = Logger.getLogger(EGCrawlerFactory.class);
	
	// configure file for this EGCrawlerFactory
	private  String confFile = null;
	// all the component names that one EGCrawler needed
	private String fetcher = null;
	private String docwriter =null;
	private String extractor =null;
	private String urlscheduler = null;
	
	// task interval in millisecond
	private int interval = 1000;
	
	// all the EGCrawler will share one URLScheduler
	private URLScheduler scheduler = null;
	/**
	 * 
	 * @param file  the configure of this factory
	 */
	public EGCrawlerFactory(String file) 
	{
		this.confFile = file;
		// read the settings from configure file 
		readfile();
		
		try
		{
			scheduler = (URLScheduler)Class.forName(this.urlscheduler).newInstance();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();				
			// if there is some exception happened , set flag to STOP, and the thread will not do any task
			scheduler = null;
		}			
	}
	
	/**
	 *  create a EGCrawler 
	 * @return
	 */
	public EGCrawler createCrawler()
	{	EGCrawler crawler = new EGCrawler();
		try
		{
			// get the name of components
			
			Fetcher fether = (Fetcher)Class.forName(this.fetcher).newInstance();
			Extractor ectractor = (Extractor)Class.forName(this.extractor).newInstance();
			DocWriter docWriter = (DocWriter)Class.forName(this.docwriter).newInstance();
			
			// add components to a EGCrawler
			if (fether == null || ectractor == null ||docWriter == null || scheduler == null)
			{
				return null;
			}
			
			// add the must componet to one EGCrawler
			crawler.setFetcher(fether);			
			crawler.setExtractor(ectractor);
			crawler.setDocWriter(docWriter);
			crawler.setScheduler(scheduler);
			
			return crawler;
		}
		catch (Exception e1)
		{
			e1.printStackTrace();				
			// if there is some exception happened , set flag to STOP, and the thread will not do any task  
			this.logger.error("create crawler error");
		}			
		return null;
	}
	
	/**
	 * read the configure file 
	 */
	private void readfile()
	{
		Properties p = new Properties();
		FileInputStream fi = null;		
		try
		{
			File f = new File(this.confFile);
			if (!f.exists() || !f.canRead() || f.isHidden())
				return ;
			fi = new FileInputStream(f);
			
			p.load(fi);
			
			 // all the component names that one EGCrawler needed 
			 fetcher = p.getProperty("fetcher");
			 docwriter = p.getProperty("docwriter");
			 extractor = p.getProperty("extractor");
			 urlscheduler = p.getProperty("urlscheduler");
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(this.confFile + " not found :" + e1.getMessage());
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(this.confFile + " io exception :" + e1.getMessage());
		}
		finally
		{
			try
			{
				if (fi != null)
					fi.close();
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * reload the setting of this EGCrawlerFactory 
	 */
	public void reload()
	{
		this.readfile();
	}
	
}
