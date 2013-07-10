package org.easyGoingCrawler.fetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.FetcherUtil;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.StatusHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpFetcherByJsoup extends Fetcher
{
	Logger loger = Logger.getLogger(HttpFetcherByJsoup.class);
	
	private WebClient webClient = null;
	private String defaultEncode = "UTF-8";
	private int conTimeout = 2;
	public HttpFetcherByJsoup(int conTimeout )
	{
		this.conTimeout = conTimeout;
	}
		
	
	@Override
	public void fetch(CrawlURI curl)
	{
		String url = curl.getUrl();
		if (url == null)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			return;
		}
		
		 try
		{
			Connection con = Jsoup.connect(url);
			con.timeout(this.conTimeout*1000);
			
			Response res = con.execute();			
			int status =  res.statusCode();
			curl.setHttpstatus(status);
			
			Document doc = con.get();
			curl.setContent(doc.html());
			
			
			String encoding = res.charset();	
			curl.setEncode(encoding == null ? this.defaultEncode:encoding);
			curl.setLastCrawlDate(new Date());	
			System.out.println(Thread.currentThread().getName()+"-"+ "##HttpFetcherByJsoup: curl="+curl);
			
			
		}
		catch (Exception e)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			curl.setContent(null);
			curl.setReserve(null);
			loger.error(Thread.currentThread().getName()+"-"+"##HttpFetcherByJsoup:"+e.getMessage());
			return;
		}		 
	}
	
	public static void main(String [] args) throws IOException
	{
		/*HttpFetcher f = new HttpFetcher ();		
		CrawlURI curl = new CrawlURI("http://www.baidu.com");
		f.fetch(curl);
		
		System.out.println(new String (curl.getContent()));
		//System.out.println(curl.getIncludeURLs());
		//f.fetch("http://www.myexception.cn/");
		//test(ret);
*/	
//		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
//		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		/*
		Fetcher fetcher = new HttpFetcherByHtmlUnit();
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://movie.douban.com/subject/6041219/?from=subject-page");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
	
		FileWriter fout = new FileWriter(new File("51cto.html"));  
		String xml = curl.getContent();
		fout.write(xml);*/
		Connection con = Jsoup.connect("http://baoku.baidu.com/search.php?word=%E6%A2%A6%E5%B9%BB%E9%A3%9E%E7%90%B4");
		
		 Document doc = con.get();
		 System.out.println(doc.html());
//		System.out.println(xml);	
//		System.out.println(curl);
	}
}
