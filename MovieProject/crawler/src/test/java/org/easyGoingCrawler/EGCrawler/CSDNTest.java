package org.easyGoingCrawler.EGCrawler;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.framwork.EGCrawler;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Query;

public class CSDNTest
{
	// test Crawler
	@Test 
	public void TestCrawler()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGCrawler crawler = appcontext.getBean("CSDNcrawler",EGCrawler.class);
		System.out.println("end");
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
	
	// test blog
	//@Test 
	public void TestHtml()
	{

		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q = new Query(where("host").is("blog.51cto.com")).limit(5);
		List<Html> htmls = Mongo.mongoOps.find( q,Html.class);
		for (Html h:htmls)
		{
			System.out.println(h);
			System.out.println(h.getHost());
			//break;
		}
	
	}	
}
