package org.easyGoingCrawler.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpFetcherTest
{
//	@Test
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
			//System.out.println(i+" " +curl);
			if(curl.getHttpstatus() == 404)
				break;
//			String content = new String (curl.getContent());
//			flag = content.contains("800");
//			System.out.println(flag+ " " +i++);
			
			try
			{
				int sleep = 5+new Random().nextInt(10);
				System.out.println("sleep"+ " "+sleep) ;
				TimeUnit.SECONDS.sleep(sleep);
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
	//@Test
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
	
	@Test
	public void test()
	{
		
	ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
	Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
	CrawlURI curl = new CrawlURI();
	curl.setUrl("https://www.googleapis.com/customsearch/v1element?key=AIzaSyCVAXiUzRYsML1Pv6RwSG1gunmMikTzQqY&rsz=filtered_cse&num=10&hl=zh_CN&prettyPrint=false&source=gcsc&gss=.com&sig=351077565dad05b6847b1f7d41e36949&cx=014545285319128157587:opxtjupf3yk&q=%E6%B3%B0%E5%9D%A6%E5%B0%BC%E5%85%8B&sort=&googlehost=www.google.com&oq=%E6%B3%B0%E5%9D%A6%E5%B0%BC%E5%85%8B");
	curl.setStatus(CrawlURI.STATUS_OK);
	fetcher.fetch(curl);
	String html = curl.getContent();
	System.out.println(html);
	}
}
