package org.easyGoingCrawler.extractor;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Extractor;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class extractorTest
{
	@Test
	public void HttpextractorTest()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/lhb25/");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		System.out.println(new String (curl.getContent()));

		
		Extractor extractor = appcontext.getBean("extractor", Extractor.class);
		extractor.extract(curl);
		
		for(String url:curl.getIncludeURLs())
		{
			System.out.println(url);
		}
		
	}
}
