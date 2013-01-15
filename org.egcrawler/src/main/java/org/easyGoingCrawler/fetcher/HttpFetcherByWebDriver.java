package org.easyGoingCrawler.fetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.FetcherUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Request;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.StatusHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpFetcherByWebDriver extends Fetcher
{
	Logger loger = Logger.getLogger(HttpFetcherByWebDriver.class);
	private WebClient webClient = null;
	
	private String defaultEncode = "UTF-8";
	private boolean enableJs = false;
	private int jsTimeout = 50;
	private int conTimeout = 20;
	WebDriver driver = null;

	public HttpFetcherByWebDriver()
	{
		
		String path ="D:/Program Files/Mozilla Firefox/firefox.exe";
		System.setProperty("webdriver.firefox.bin",path);
		driver = new FirefoxDriver();
		// TODO Auto-generated constructor stub
		webClient = new WebClient();
		webClient.getCache().setMaxSize(100);	
		webClient.setRedirectEnabled(true);
		webClient.setTimeout(conTimeout*1000);
		webClient.setThrowExceptionOnFailingStatusCode(false);
		webClient.setJavaScriptEnabled(enableJs);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setCssEnabled(false);
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
		{   HtmlPage page = webClient.getPage(url);
			int status =page.getWebResponse().getStatusCode();
			curl.setStatus(status);
			
			if(status != 200)
				return;
	
			WebResponse webresponse = page.getWebResponse();
			String encoding = webresponse.getContentCharsetOrNull();			
			curl.setEncode(encoding == null ? this.defaultEncode:encoding);
			curl.setLastCrawlDate(new Date());	
			page.cleanUp();
			page = null;
			
			driver.get(curl.getUrl());
			String res = driver.getPageSource();
			curl.setContent(res);
			System.out.println(Thread.currentThread().getName()+"-"+ "##HttpFetcherByHtmlUnit: curl="+curl);
			curl.setReserve(driver);

		} catch (Exception e)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			curl.setContent(null);
			curl.setReserve(null);
			loger.error(Thread.currentThread().getName() + "-"
					+ "##HttpFetcherByHtmlUnit:" + e.getMessage());
			return;
		}
	}


	public static void main(String[] args) throws IOException
	{
		/*
		 * HttpFetcher f = new HttpFetcher (); CrawlURI curl = new
		 * CrawlURI("http://www.baidu.com"); f.fetch(curl);
		 * 
		 * System.out.println(new String (curl.getContent()));
		 * //System.out.println(curl.getIncludeURLs());
		 * //f.fetch("http://www.myexception.cn/"); //test(ret);
		 */
		// ApplicationContext appcontext = new
		// ClassPathXmlApplicationContext("springcofigure.xml");
		// Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);

		Fetcher fetcher = new HttpFetcherByWebDriver();
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/baihmpgy/archive/2013/01/06/2847449.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);

/*		FileWriter fout = new FileWriter(new File("51cto.html"));
		String xml = curl.getContent();
		fout.write(xml);*/
		
		System.out.println(curl.getContent());
		// Document doc = Jsoup.parse(xml);
		// System.out.println(doc.text());
		// System.out.println(xml);
		// System.out.println(curl);
	}
}
