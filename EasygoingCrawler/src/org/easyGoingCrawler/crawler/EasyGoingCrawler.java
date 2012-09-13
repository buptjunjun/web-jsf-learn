package org.easyGoingCrawler.crawler;

import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.docWriter.MirrorWriter;
import org.easyGoingCrawler.extractor.HTMLExtractor;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.framwork.CrawlTask;
import org.easyGoingCrawler.policy.URLPolicy;
import org.easyGoingCrawler.urlStore.URLStoreMysql;

/**
*  
*  The Main Method of EasyGoing crawler
*  @author Andy  weibobee@gmail.com  2012-9-12
*
*/
public class EasyGoingCrawler 
{
	public  static void  main(String [] args)
	{
		CrawlTask task = new CrawlTask(null);
		task.setUrlStore(new URLStoreMysql());
		task.setExtractor(new HTMLExtractor());
		task.setExtractPolicy(new URLPolicy());
		task.setFetcher(new HttpFetcher());
		task.setFetchPolicy(new URLPolicy());
		task.setDocWriter(new MirrorWriter());
		Thread t = new Thread(task);
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
