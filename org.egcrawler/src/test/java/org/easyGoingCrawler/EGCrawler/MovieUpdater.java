package org.easyGoingCrawler.EGCrawler;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyGoingCrawler.DAO.DAOMongo;
import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.analyzer.CSDNBlogAnalyzer;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.EGCrawler;
import org.easyGoingCrawler.framwork.Fetcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Query;

public class MovieUpdater
{
	static String host = "movie.douban.com";
	// test Crawler
	@Test 
	public void TestCrawler()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
	
		DAOMongo dao = (DAOMongo) appcontext.getBean("DAOMongo");
		Map constrain = new HashMap<String,String>();
		constrain.put("magicNum", -1);
		List<Movie> lhtml = dao.search(constrain, 2, Movie.class);
		
		System.out.println();
		
	}
	
	// test blog
	//@Test 
	public void TestBlog()
	{

		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);
		List<Blog> blog = Mongo.mongoOps.find( q_sochina,Blog.class);
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
		
		List<Bloger> bloger = Mongo.mongoOps.find( q_sochina,Bloger.class);
		for (Bloger b:bloger)
		{
			System.out.println(b);
			//break;
		}
	}
	
}
