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

public class HttpFetcherByHtmlUnit extends Fetcher
{
	Logger loger = Logger.getLogger(HttpFetcherByHtmlUnit.class);
	
	private WebClient webClient = null;
	private String defaultEncode = "UTF-8";
	private boolean enableJs = true;
	private int jsTimeout = 50;
	private int conTimeout = 20;
	public HttpFetcherByHtmlUnit()
	{
		// TODO Auto-generated constructor stub
		this(false);
	}
	
	public HttpFetcherByHtmlUnit(boolean enableJs)
	{
		// TODO Auto-generated constructor stub
		this(enableJs ,50,20);
	}
   
	public HttpFetcherByHtmlUnit(boolean enableJs, int jsTimeout, int conTimeout)
	{
		this.enableJs = enableJs;
		if(jsTimeout >0 && conTimeout > 0)
		{
			this.jsTimeout = jsTimeout;
			this.conTimeout = conTimeout;
		}

		// TODO Auto-generated constructor stub
		webClient = new WebClient();
		webClient.getCache().setMaxSize(100);	
		webClient.setRedirectEnabled(true);
		webClient.setTimeout(conTimeout*1000);
		webClient.setThrowExceptionOnFailingStatusCode(false);
		webClient.setJavaScriptEnabled(enableJs);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setCssEnabled(false);
		if(this.enableJs)
		{
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());		
			webClient.setJavaScriptTimeout(jsTimeout*1000);
			webClient.waitForBackgroundJavaScript(jsTimeout*1000);

		}
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
			//System.out.println(page.asXml());
			WebResponse webresponse = page.getWebResponse();
			String res = webresponse.getContentAsString(page.getWebResponse().getContentCharset());		
			curl.setContent(res);
						
			int status = webresponse.getStatusCode();
			curl.setHttpstatus(status);
			
			String encoding = webresponse.getContentCharsetOrNull();			
			curl.setEncode(encoding == null ? this.defaultEncode:encoding);
			curl.setLastCrawlDate(new Date());	
			curl.setReserve(page);
			loger.error(Thread.currentThread().getName()+"-"+ "##HttpFetcherByHtmlUnit: curl="+curl);
			
		}
		catch (Exception e)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			curl.setContent(null);
			curl.setReserve(null);
			loger.error(Thread.currentThread().getName()+"-"+"##HttpFetcherByHtmlUnit:"+e.getMessage());
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
		curl.setUrl("http://www.cnblogs.com/baihmpgy/archive/2013/01/06/2847449.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
	
		FileWriter fout = new FileWriter(new File("51cto.html"));  
		String xml = curl.getContent();
		fout.write(xml);
		//Document doc = Jsoup.parse(xml);
		//System.out.println(doc.text());
//		System.out.println(xml);	
//		System.out.println(curl);
	}
}
