package org.easyGoingCrawler.extractor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.crawler.EGCrawlerFactory;
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
		if (curl.getContent() == null || curl.getEncode() == null)
		{
			curl.setStatus(false);
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
			curl.setStatus(false);
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
			  /* if (!url.startsWith("http://"))
			   {
				   if (originalURL.endsWith("/"))
				      url = originalURL+ url;
				   else
				   {
					   int index = originalURL.lastIndexOf("/");
					   String u = originalURL.substring(0,index+1);
					   url = u+url;
				   }
			   }*/
			   //System.out.println(url);
			   if (url.startsWith("http://"));
			   		urls.add(url);
				
			}
						
			curl.setIncludeURLs(urls);
			curl.setStatus(true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("extract "+ originalURL +" error " + e.getMessage());
			curl.setStatus(false);
			return;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{  
		String file = "www.myexception.cn/apache/index.html";
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
		}
		
	}
}
