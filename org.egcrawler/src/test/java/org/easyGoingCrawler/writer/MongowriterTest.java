package org.easyGoingCrawler.writer;

import java.util.List;

import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.analyzer.CSDNBlogerAnalyzer;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.MirrorWriter;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MongowriterTest
{
	//@Test
	public void test()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		DocWriter docwriter = appcontext.getBean("MongoHtmlWriter",DocWriter.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.csdn.net/a9529lty/article/details/7008537");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("blog.csdn.net");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		docwriter.write(curl);
		//Bloger bloger = new CSDNBlogerAnalyzer().analyze(null, curl.getEncode(), curl.getContent());
	}
	
	@Test
	public void testRead()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");

		EGDAOMongo mongo = appcontext.getBean("EGDAOMongo",EGDAOMongo.class);
	
		List<Html> l = mongo.getLatestHtml("www.cnblogs.com", 10);
		MirrorWriter mirrorwriter = appcontext.getBean("mirrorwriter",MirrorWriter.class);
		for(Html h:l)
		{
			CrawlURI curl = new CrawlURI();
			curl.setHttpstatus(200);
			curl.setUrl(h.getUrl());
			curl.setEncode(h.getEncode());
			curl.setContent(h.getHtml());
			curl.setHost(h.getHost());
			mirrorwriter.write(curl);
		}
		
	}
}
 