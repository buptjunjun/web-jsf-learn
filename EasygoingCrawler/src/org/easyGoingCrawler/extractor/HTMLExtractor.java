package org.easyGoingCrawler.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import  org.easyGoingCrawler.framwork.Extractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *  extract url from  HTML document
 *  Use jsoup to extract the urls
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class HTMLExtractor implements Extractor
{
	public HTMLExtractor ()
	{
	}
	
	/**
	 * extract urls from the document 
	 * @param docContent for example a html 
	 * @return list of the url in this document
	 */
	@Override
	public List<String> extract(String docContent)
	{
		if (docContent == null)
			return null;
		
		try
		{
			// create a jsoup Document from a string
			Document doc = Jsoup.parse(docContent);
	
			// find the elments whose type is "<a>" and  value of  "href"  begins with "http";  
			Elements hrefs = doc.select("a[href^=http]");
			
			if (hrefs == null)
				return null;
			
			// get all the url in this documents
			List<String>  urls = new ArrayList<String>();
			for (Element e: hrefs)
			{
				// get a url in an element like e<a href="www.abc.com/aa">;
				String url = e.attr("href");
				urls.add(url);
			}
						
			return urls ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{  
		
	}
}
