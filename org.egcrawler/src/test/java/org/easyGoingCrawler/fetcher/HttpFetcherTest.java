package org.easyGoingCrawler.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpFetcherTest
{
	@Test
	public void FetcherTest()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherProxy",Fetcher.class);
		
		String [] urls = new String[] {"http://pjgod.iteye.com/blog/1757100",
							"http://shuaigelingfei.iteye.com/blog/1757096",
							"http://410063005.iteye.com/blog/1757054"
							};
			
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://pjgod.iteye.com/blog/1757100");
		curl.setStatus(CrawlURI.STATUS_OK);
		boolean flag = true;
		int i = 0;
		while(flag)
		{
			curl.setUrl(urls[i++%3]);
			curl.setHttpstatus(-1);
			fetcher.fetch(curl);
			System.out.println(i+" " +curl);
			if(curl.getHttpstatus() != 200)
				break;
//			String content = new String (curl.getContent());
//			flag = content.contains("800");
//			System.out.println(flag+ " " +i++);
			
			try
			{
				TimeUnit.SECONDS.sleep(50);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		PropertyPlaceholderConfigurer ppf = appcontext.getBean("propertyConfigurer", PropertyPlaceholderConfigurer.class);
//		String testProps = appcontext.getBean("testProps", String.class);
//		System.out.println(testProps);
	}
	@Test
	public void TestIteye()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherProxy",Fetcher.class);
		
		String [] urls = new String[] {"http://pjgod.iteye.com/blog/1757100",
							"http://shuaigelingfei.iteye.com/blog/1757096",
							"http://410063005.iteye.com/blog/1757054"
							};
			
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://pjgod.iteye.com/blog/1757100");
		curl.setStatus(CrawlURI.STATUS_OK);
		boolean flag = true;
		int i = 0;
		while(flag)
		{
			curl.setUrl(urls[i++%3]);
			curl.setHttpstatus(-1);
			fetcher.fetch(curl);
			System.out.println(i+" " +curl);
			if(curl.getHttpstatus() != 200)
				break;
//			String content = new String (curl.getContent());
//			flag = content.contains("800");
//			System.out.println(flag+ " " +i++);
			
			try
			{
				TimeUnit.SECONDS.sleep(50);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
