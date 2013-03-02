package org.easyGoingCrawler.fetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

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
import org.openqa.selenium.ie.InternetExplorerDriver;
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
	private int driverTimeout = 20;
	private int conTimeout = 20;
	WebDriver driver = null;
	FirefoxProfile firefoxProfile =null;
	private long fetchcount = 1;
	
	public HttpFetcherByWebDriver()
	{
		
		String path ="";
		System.setProperty("webdriver.firefox.bin","E:\\Program Files\\Mozilla Firefox\\firefox.exe");
		//System.setProperty("webdriver.ie.driver","C:/Program Files (x86)/Internet Explorer/iexplore.exe");
	
		// 上边是设置firefox可执行文件的路径			
		// 关图片
		firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("permissions.default.image", 2);
		
		// 关掉flash
		firefoxProfile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
		// 禁用css,不方便调试了。。
		//firefoxProfile.setPreference("permissions.default.stylesheet", 2);
		// 启动快速加载，不过好像没什么改变。照官方说法在load结束前就可以开始操作，不过我这还是被blocked直到页面加载完毕   
	    //firefoxProfile.setPreference("webdriver.load.strategy", "unstable");

		//driver = new InternetExplorerDriver();
		/*File file1 = new File("C:/Users/junjun/AppData/Roaming/Mozilla/Firefox/Profiles/p8y3jpgb.default/extensions/requestpolicy@requestpolicy.com.xpi");
		try
		{
			firefoxProfile.addExtension(file1);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		driver = new FirefoxDriver(firefoxProfile);
		
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
		{   
			try
			{
				FetchTimerTask task = new FetchTimerTask(driver,curl.getUrl());
				task.start();
				int count = 0;
				while(!task.isOver() && count < 60)
				{
					TimeUnit.SECONDS.sleep(1);
					count++;					
				}
				if(!task.isOver())
				{					
					curl.setHttpstatus(-1);
					task.interrupt();
				}
				else
				{
					curl.setHttpstatus(200);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			fetchcount++;
			System.out.println("++++++++++++"+fetchcount+"+++++++++++++++++++");
			String res = driver.getPageSource();
					
			curl.setEncode("gb2312");
			curl.setLastCrawlDate(new Date());	
			curl.setContent(res);
			System.out.println(Thread.currentThread().getName()+"-"+ "##HttpFetcherByWebDriver: curl="+curl);
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
	
	private class FetchTimerTask extends Thread
	{
		private WebDriver driver = null; 
		private String url;
		private boolean over = false;
		

		public FetchTimerTask( WebDriver driver,String url)
		{
			// TODO Auto-generated constructor stub
			this.driver = driver;
			this.url = url;
		}

		@Override
		public void run()
		{
			over = false;
			driver.get(url);
			over = true;
		}
		
		public boolean isOver()
		{
			return over;
		}

		public void setOver(boolean over)
		{
			this.over = over;
		}
		
	}
}
