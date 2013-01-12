package org.easyGoingCrawler.writer;

import org.easyGoingCrawler.analyzer.CSDNBlogerAnalyzer;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MongoHtmlWriterTest
{
	@Test
	public void test()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		DocWriter docwriter = appcontext.getBean("docwriter",DocWriter.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.csdn.net/a9529lty/article/details/7008537");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("blog.csdn.net");
		fetcher.fetch(curl);
		//System.out.println(curl.getContent());
		docwriter.write(curl);
		//Bloger bloger = new CSDNBlogerAnalyzer().analyze(null, curl.getEncode(), curl.getContent());
	}
}
 