package org.easyGoingCrawler.urlscheduler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Extractor;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.framwork.URLScheduler;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RotateHostURLSchedulerTest
{

	@Test
	public void test()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
//		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
//		
//		CrawlURI curl = new CrawlURI();
//		curl.setUrl("http://www.baidu.com");
//		curl.setStatus(CrawlURI.STATUS_OK);
//		fetcher.fetch(curl);
//		System.out.println(new String (curl.getContent()));
//
//		
//		Extractor extractor = appcontext.getBean("extractor", Extractor.class);
//		extractor.extract(curl);
//		
//		System.out.println(curl.getIncludeURLs());
		
		RotateHostURLScheduler urlscheduler = appcontext.getBean("csdnscheduleer", RotateHostURLScheduler.class);
		urlscheduler.insertSeeds();
		CrawlURI curl = urlscheduler.get();
		
		System.out.println(curl);

		
	}

}
