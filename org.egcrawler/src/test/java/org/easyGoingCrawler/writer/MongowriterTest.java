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
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnitJs",Fetcher.class);
		DocWriter docwriter = appcontext.getBean("MongoHtmlWriter",DocWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/jiagoushi/archive/2013/01/23/2872824.html");
		//curl.setUrl("http://blog.csdn.net/a9529lty/article/details/7008537");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.cnblogs.com");
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
	
		List<Html> l = mongo.getLatestHtml("www.iteye.com/blogs", 3);
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
 