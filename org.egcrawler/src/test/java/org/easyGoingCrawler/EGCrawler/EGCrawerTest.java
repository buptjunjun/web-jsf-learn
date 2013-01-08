package org.easyGoingCrawler.EGCrawler;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.EGCrawler;
import org.easyGoingCrawler.urlscheduler.RotateHostURLScheduler;
import org.easyGoingCrawler.util.URLAnalyzer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EGCrawerTest
{

	@Test
	public void test()
	{
		
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGCrawler crawler = appcontext.getBean("CSDNcrawler",EGCrawler.class);
		crawler.startCrawl();
		crawler.start();
		
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
