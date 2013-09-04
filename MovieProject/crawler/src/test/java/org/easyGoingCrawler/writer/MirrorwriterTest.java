package org.easyGoingCrawler.writer;

import java.io.File;
import java.io.IOException;


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
		curl.setUrl("http://my.oschina.net/bairrfhoinn/blog/106138");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("my.oschina.net");
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
	
	public void testOSChina()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnitJs",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("mirrorwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/jiagoushi/archive/2013/01/23/2872824.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.cnblogs.com");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
	}
	//@Test
	public void testCnblogs()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnitJs",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("mirrorwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/jiagoushi/archive/2013/01/23/2872824.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.cnblogs.com");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
	}
	
	@Test
	public void testIbmcn()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnitJs",Fetcher.class);
		MirrorWriter docwriter = appcontext.getBean("mirrorwriter",MirrorWriter.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/jiagoushi/archive/2013/01/23/2872824.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.cnblogs.com");
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		String path = docwriter.getPath(curl);
		//System.out.println(path);
		docwriter.write(curl);
	   
	}
	
}
 