package org.easyGoingCrawler.fetcher;

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
/*		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.baidu.com");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		System.out.println(new String (curl.getContent()));*/
		
		PropertyPlaceholderConfigurer ppf = appcontext.getBean("propertyConfigurer", PropertyPlaceholderConfigurer.class);
		String testProps = appcontext.getBean("testProps", String.class);
		System.out.println(testProps);
	}
}
