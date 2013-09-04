package org.easyGoingCrawler.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.taglibs.standard.resources.Resources;
import org.easyGoingCrawler.framwork.*;
import org.easyGoingCrawler.util.Localizer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;



/**
*  
*  The entrance of EasyGoing crawler
*  @author Andy  weibobee@gmail.com  2012-9-13
*
*/
public class Main 
{
	
	static private Logger logger = Logger.getLogger(Main.class);
	private ApplicationContext appcontext = null;
	public Main()
	{
		appcontext = new ClassPathXmlApplicationContext("egcrawler.xml");
		String log4jfile = Localizer.getMessage("log4jFile");
		PropertyConfigurator.configure(log4jfile);
		
	}
	
	
	public  static void  main(String [] args)
	{	
		Main main = new Main();
	//	String [] crawler = {"CSDNcrawler","ChinaUnixcrawler","OsChinacrawler","A51ctocrawler","Cnblogscrawler","Ibmcncrawler","Iteyecrawler"};
	//	String [] threads = {"csdn_threads","chinaunix_threads","oschina_threads","a51cto_threads","cnblogs_threads","ibmcn_threads","iteye_threads"};

		String crawlerStr = Localizer.getMessage("crawlers");
		String threadsStr = Localizer.getMessage("threads");
		
		String [] crawler = crawlerStr.trim().split(" ");
		String [] threads = threadsStr.trim().split(" ");
		
		for(int i = 0; i < crawler.length; i++)
			main.createAndStartCrawler(crawler[i],threads[i]);
		
		while(true)
		{
			
			try
			{
				TimeUnit.SECONDS.sleep(100);
				System.out.println("sleep 1 second");
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
	
	public void  createAndStartCrawler(String crawlerName,String threads)
	{
		int poolSize = 3;
		
		String psize = Localizer.getMessage(threads);
		try
		{
			poolSize = Integer.parseInt(psize);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			poolSize = 3;
		}
		
		
		for(int i = 0; i < poolSize; i++)
		{
			EGCrawler egcrawler = appcontext.getBean(crawlerName, EGCrawler.class);
			egcrawler.setName("="+crawlerName+" "+i+":" );				
			egcrawler.start();			
			egcrawler.startCrawl();
		}
	}
}
