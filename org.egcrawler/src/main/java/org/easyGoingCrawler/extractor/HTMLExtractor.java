package org.easyGoingCrawler.extractor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.crawler.EGCrawlerFactory;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.framwork.CrawlURI;
import  org.easyGoingCrawler.framwork.Extractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *  extract url from  FetchedFile document
 *  Use jsoup to extract the urls
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class HTMLExtractor extends Extractor
{
	private Logger logger = Logger.getLogger(EGCrawlerFactory.class);
	private int  MaxURLSize = 246;
	public HTMLExtractor ()
	{
	}
	
	/**
	 * extract urls from the document 
	 * @param docContent for example a html 
	 * @return list of the url in this document
	 */
	@Override
	public void extract(CrawlURI curl)
	{
		if (curl.getStatus() !=CrawlURI.STATUS_OK || curl.getContent() == null || curl.getEncode() == null || curl.getHttpstatus()!=200)
		{
			logger.info("");
			return;
		}
		
		String originalURL = curl.getUrl();
		String encode = curl.getEncode();
		String docContent = null;
		try 
		{
			docContent = new String(curl.getContent(),encode);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			
			e1.printStackTrace();
			logger.error("error :new String(curl.getContent(),encode) " + e1.getMessage());
			curl.setStatus(CrawlURI.STATUS_EXTRACT_ERROR);
			return;
		}
		
		try
		{
			// create a jsoup Document from a string
			Document doc = Jsoup.parse(docContent);
	
			// find the elments whose type is "<a>" and  value of  "href"  begins with "http";  
			Elements hrefs = doc.select("a");
			
			if (hrefs == null)
				return ;
			
			// get all the url in this documents
			List<String>  urls = new ArrayList<String>();
			for (Element e: hrefs)
			{
				// get a url in an element like <a href="http://www.abc.com/aa">;
				String url = e.attr("href");				
				
				// if the url is relative url like <a href="/aa.html">
			   if (url.startsWith("/"))
			   {
				   url = doRelativeURL(originalURL,url);
				   
				   if(url.length() < MaxURLSize)
					   urls.add(url);
				   
				   //System.out.println(url +"  " + e.hasText() + " " + e.html());
			   }
			   else if (url.startsWith("http://"))
			   {
				   if(url.length() < MaxURLSize)
					   urls.add(url);
				 //  System.out.println(url + "  " + e.hasText() + " " + e.html());
			   }
			}
			System.out.println("extract " + urls.size() + "urls from " + originalURL);
			curl.setIncludeURLs(urls);
			curl.setStatus(CrawlURI.STATUS_OK);
			
			System.out.println(Thread.currentThread().getName()+" extract curl: "+ curl.toString());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("extract "+ originalURL +" error " + e.getMessage());
			curl.setStatus(CrawlURI.STATUS_EXTRACT_ERROR);
			return;
		}
	}

	
	/**
	 *  deal with the relative url;
	 *  for example: /abc/a.html is a url from http://baidu.com/asdfa,
	 *  so the absolute url should be http://baidu.com/abc/a.html
	 *  
	 * @param url
	 * @param path
	 * @return
	 */
	private String doRelativeURL(String url, String path)
	{
		String ret = null;
		URL  u = null;
		try 
		{
			u = new URL(url);
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			logger.error("doRelativeURL error  "+ "url = "+ url+", path = " + path+"   "+ e.getMessage());
			return null;
		}
		
		String host = u.getHost();
		String protocal = u.getProtocol();
		if( host == null || path == null)
			 return null;
		
		ret = protocal+"://"+ host + path;
		
		return ret;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{  
	/*	String file = "www.myexception.cn/apache/index.html";
		try {
			BufferedReader  fr = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = fr.readLine()) != null)
			{
				sb.append(line);
			}
			String content = sb.toString();
			HTMLExtractor extractor = new HTMLExtractor();
			//extractor.extract("http://www.myexception.cn/apache",content );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			URL u = new URL("http://www.iteye.com/blogs/category/architecture");
			
			System.out.println(u.getPort());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HttpFetcher f = new HttpFetcher ();		
		CrawlURI curl = new CrawlURI("http://www.iteye.com/blogs/tag/应用服务器?page=4");
		f.fetch(curl);
		
		HTMLExtractor extractor = new HTMLExtractor ();
		extractor.extract(curl);
		
		System.out.println(curl.getHttpstatus());
		System.out.println(curl.getStatus());
		System.out.println(curl.getIncludeURLs());
		
	}
}
