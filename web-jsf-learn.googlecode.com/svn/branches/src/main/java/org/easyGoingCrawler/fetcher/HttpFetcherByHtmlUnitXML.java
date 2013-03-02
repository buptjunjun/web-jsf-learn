package org.easyGoingCrawler.fetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Date;

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

public class HttpFetcherByHtmlUnitXML extends Fetcher
{
	private WebClient webClient = null;
	private String defaultEncode = "UTF-8";
	private boolean enableJs = true;
	private int jsTimeout = 30;
	private int conTimeout = 20;
	public HttpFetcherByHtmlUnitXML()
	{
		// TODO Auto-generated constructor stub
		webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
		webClient.getCache().setMaxSize(100);	
		webClient.setRedirectEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setTimeout(conTimeout*1000);
		webClient.setCssEnabled(true);
		if(this.enableJs)
		{
			webClient.setJavaScriptEnabled(enableJs);
			webClient.setJavaScriptTimeout(jsTimeout*1000);
			webClient.waitForBackgroundJavaScript(jsTimeout*1000);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		}
	}
   
	public HttpFetcherByHtmlUnitXML(boolean enableJs, int jsTimeout, int conTimeout)
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
		webClient.setJavaScriptEnabled(enableJs);	
		webClient.setRedirectEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setTimeout(conTimeout*1000);
		
		if(this.enableJs)
		{
			webClient.setJavaScriptTimeout(jsTimeout*1000);
			webClient.waitForBackgroundJavaScript(jsTimeout*1000);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
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
			//System.out.println();
			WebResponse webresponse = page.getWebResponse();
			curl.setContent(page.asXml());
			
			int status = webresponse.getStatusCode();
			curl.setHttpstatus(status);
			
			String encoding = webresponse.getContentCharsetOrNull();			
			curl.setEncode(encoding == null ? this.defaultEncode:encoding);
			curl.setLastCrawlDate(new Date());	
			curl.setReserve(page);
			
		}
		catch (Exception e)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			curl.setContent(null);
			curl.setReserve(null);
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
		
		Fetcher fetcher = new HttpFetcherByHtmlUnitXML();
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/yemeishu/archive/2013/01/06/2847156.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
	
		FileWriter fout = new FileWriter(new File("51cto.html"));  
		String xml = curl.getContent();
		//fout.write(xml, 0, xml.length());
		//Document doc = Jsoup.parse(xml);
		//System.out.println(doc.text());
//		System.out.println(xml);	
//		System.out.println(curl);
	}
}
