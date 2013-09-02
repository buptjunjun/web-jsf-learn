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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.easyGoingCrawler.fetcher.HttpFetcherByHtmlUnit;
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
	private Logger logger = Logger.getLogger(HTMLExtractor.class);
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
		if (curl.getStatus() !=CrawlURI.STATUS_OK || curl.getContent() == null || curl.getEncode() == null )
		{
			logger.info("");
			return;
		}
		
		String originalURL = curl.getUrl();
		String encode = curl.getEncode();
		String docContent = null;
		docContent = curl.getContent();	
		
		try
		{
			// create a jsoup Document from a string
			Document doc = Jsoup.parse(docContent);
	
			// find the elments whose type is "<a>" and  value of  "href"  begins with "http";  
			Elements hrefs = doc.select("a[href]");
			
			if (hrefs == null)
				return ;
			System.out.println("hrefs = "+hrefs.size());
			// get all the url in this documents
			Set<String>  urls = new HashSet<String>();
			for (Element e: hrefs)
			{
				// get a url in an element like <a href="http://www.abc.com/aa">;
				String url = e.attr("href");				
				if(url == null)
					continue;
				
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
			   else if (url.startsWith("?"))
			   {
				   url = doRelativeURL1(originalURL,url);
				   if( url!=null && url.length() < MaxURLSize)
				   	urls.add(url);
			   }
			}
			System.out.println("extract " + urls.size() + " urls from " + originalURL);
			List l = new ArrayList<String>();
			
			for(String url:urls)
			{
				String tmp = url.trim();
				if(tmp.endsWith("/"))
				{
					int len = tmp.lastIndexOf("/");
					tmp = tmp.substring(0,len);
				}
				l.add(tmp);
			}
			curl.setIncludeURLs(l);
			curl.setStatus(CrawlURI.STATUS_OK);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(Thread.currentThread().getName()+"-"+ "$$HTMLExtractor: "+ e.getMessage());
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
			
			logger.error(Thread.currentThread().getName()+"-"+ "$$HTMLExtractor: "+ "url = "+ url+", path = " + path+"   "+ e.getMessage());
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
	 *  deal with the relative url;
	 *  for example:?code=aaa is a url from http://baidu.com/search?adsfcef,
	 *  so the absolute url should be http://baidu.com/search?code=aaa
	 *  
	 * @param url
	 * @param path
	 * @return
	 */
	private String doRelativeURL1(String url, String path)
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
			
			logger.error(Thread.currentThread().getName()+"-"+ "$$HTMLExtractor: "+ "url = "+ url+", path = " + path+"   "+ e.getMessage());
			return null;
		}
		if(!url.contains("?"))
			return null;
		
		String newurl = url.replaceAll("\\?.*", "");
		newurl+=path;
		
		return newurl;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{  
		HTMLExtractor e = new HTMLExtractor();
		
		String base = "http://www.oschina.net/search?scope=blog&q=%E8%AE%A1%E7%AE%97";
		String str = "?scope=blog&user=0&lang=0&sort=default&q=%E8%AE%A1%E7%AE%97&p=1";
		System.out.println(e.doRelativeURL1(base, str));
	}
}
