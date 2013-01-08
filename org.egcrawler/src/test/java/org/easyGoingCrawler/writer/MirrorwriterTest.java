package org.easyGoingCrawler.writer;

import java.io.File;
import java.io.IOException;

import org.easyGoingCrawler.analyzer.CSDNBlogerAnalyzer;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.MirrorWriter;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.framwork.Fetcher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MirrorwriterTest
{
	//@Test
	public void test51cto()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("docwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://tsun424.blog.51cto.com/1048838/437752");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("blog.51cto.com");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
		
		//Bloger bloger = new CSDNBlogerAnalyzer().analyze(null, curl.getEncode(), curl.getContent());
	}
	
	//@Test
	public void testCsdn()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("docwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://blog.csdn.net/guoqingchun/article/details/8471604");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("blog.csdn.net");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
	}
	
	//@Test
	public void testChinaunix()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("docwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://blog.chinaunix.net/uid-28451457-id-3461415.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("blog.chinaunix.net");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
	}
	
	
	@Test
	public void testOSChina()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("docwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/baihmpgy/archive/2013/01/06/2847449.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("my.oschina.net");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
	}
	
}
 