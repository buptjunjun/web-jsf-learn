package org.easyGoingCrawler.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.framwork.*;
import org.easyGoingCrawler.setting.EGCrawlerSetting;


/**
*  
*  The Main Method of EasyGoing crawler
*  @author Andy  weibobee@gmail.com  2012-9-12
*
*/
public class Main 
{
	public  static void  main(String [] args)
	{

		EGCrawlerSetting setting = new EGCrawlerSetting("conf/setting.properties");
		
		EGCrawler task = new EGCrawler(setting);
		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();
		
		
		try
		{
			System.out.println("main thread sleep 5 seconds");
			TimeUnit.SECONDS.sleep(5);
			task.pause();
			task.start();
			
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		try
		{
			System.out.println(" main thread sleep 5 seconds");
			TimeUnit.SECONDS.sleep(5);
			task.pause();
			
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try
		{
			System.out.println(" main thread sleep 5 seconds");
			TimeUnit.SECONDS.sleep(5);
			task.start();
			
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			System.out.println(" main thread sleep 5 seconds");
			TimeUnit.SECONDS.sleep(5);
			task.stop();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
