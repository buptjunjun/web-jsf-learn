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
import org.easyGoingCrawler.framwork.*;



/**
*  
*  The entrance of EasyGoing crawler
*  @author Andy  weibobee@gmail.com  2012-9-13
*
*/
public class Main 
{
	static
	{
		PropertyConfigurator.configure("conf/log4j.properties");
	}
	
	static private Logger logger = Logger.getLogger(Main.class);
	public  static void  main(String [] args)
	{
		logger.info("hello ");
		EGCrawlerPool p = new EGCrawlerPool();
		p.addNCrawler(3);
		
		while(true)
		{
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
