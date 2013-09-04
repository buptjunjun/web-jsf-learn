package org.easyGoingCrawler.DAO;


import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class EGDAOMongoTest
{
	@Test
	public void test()
	{
		Html h = new Html();
		h.setId("12345");
		h.setHost("aaa");
		h.setHtml("bbb");
		
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("egcrawler.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Mongo.insert(h);
	}
}
 