package org.easyGoingCrawler.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.easyGoingCrawler.framwork.EGCrawler;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.framwork.Extractor;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.framwork.Policy;
import org.easyGoingCrawler.framwork.URLStore;
import org.easyGoingCrawler.setting.EGCrawlerSetting;

/**
 * EGCrawlerPool is a pool of EGCrawler threads, it is the controller of all the EGCrawler instances.
 * it will take responsibility for creating  ,starting ,pausing and stop  a EGCrawler thread;
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */

public class EGCrawlerPool
{

	// EGcrawler Setting information 
	EGCrawlerSetting crawlerSetting = null;
	
	public EGCrawlerPool(String confFile)
	{
		this.crawlerSetting = new EGCrawlerSetting(confFile);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	

}
