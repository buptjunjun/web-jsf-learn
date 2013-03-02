package org.easyGoingCrawler.EGCrawler;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.analyzer.CSDNBlogAnalyzer;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.EGCrawler;
import org.easyGoingCrawler.framwork.Fetcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

public class IBMTest
{
	// test Crawler
	//@Test 
	public void TestCrawler()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		for(int i = 0; i < 3; i++)
		{
			EGCrawler crawler = appcontext.getBean("A51ctocrawler",EGCrawler.class);
			crawler.setName(crawler.getHost()+"-crawler-"+i+" ");
			crawler.startCrawl();
			crawler.start();
		}
		
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
	@Test 
	public void TestBlog()
	{

		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_51cto = new Query(where("host").is("ibm.cn")).limit(5);
		q_51cto.sort().on("date", Order.DESCENDING);
		List<Blog> blog = Mongo.mongoOps.find( q_51cto,Blog.class);
		for (Blog b:blog)
		{
			System.out.println(b);
			System.out.println(b.getContent());
			//break;
		}
	
	}
	
	// test bloger
	//@Test 
	public void TestBloger()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);
		
		List<Bloger> bloger = Mongo.mongoOps.find( q_51cto,Bloger.class);
		for (Bloger b:bloger)
		{
			System.out.println(b);
			//break;
		}
	}
	
	// test bloger
	//@Test 
	public void TestURl()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);
		
		List<CrawlURI> urls = Mongo.get("blog.51cto.com");
		for (CrawlURI u:urls)
		{
			System.out.println(u);
			//break;
		}
	}
	
}
