package org.easyGoingCrawler.fetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Date;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.FetcherUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.StatusHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpFetcherByHtmlUnit extends Fetcher
{
	private WebClient webClient = null;
	
	private int timeout = 20000;
	private String defaultEncode = "UTF-8";
	public HttpFetcherByHtmlUnit()
	{
		// TODO Auto-generated constructor stub
		webClient = new WebClient();
		//webClient.setTimeout(timeout);
		//webClient.setJavaScriptEnabled(false);
		//webClient.setThrowExceptionOnFailingStatusCode(false);
		webClient.getCache().setMaxSize(100);

		webClient.setJavaScriptEnabled(true);
		webClient.setJavaScriptTimeout(3600*1000);
		webClient.setRedirectEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setTimeout(3600*1000);

		webClient.waitForBackgroundJavaScript(600*1000);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
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
			HtmlPage page = webClient.getPage(url);
			String xml = page.asXml();
			curl.setContent(xml.getBytes());
			WebResponse webresponse = page.getWebResponse();			
			int status = webresponse.getStatusCode();
			curl.setHttpstatus(status);
			
			String encoding = webresponse.getContentCharsetOrNull();			
			curl.setEncode(encoding == null ? this.defaultEncode:encoding);
			curl.setLastCrawlDate(new Date());		
			page = null;
		}
		catch (Exception e)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			curl.setContent(null);
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
		
		Fetcher fetcher = new HttpFetcherByHtmlUnit();
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/58top/archive/2012/12/28/how-to-generate-unique-promotion-discount-codes-in-php.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
	
		System.out.println(new String (curl.getContent()));	
		System.out.println(curl);
	}
}
