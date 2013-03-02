package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CSDNBlogerAnalyzer implements Analyzer<Bloger>
{
	public CSDNBlogerAnalyzer()
	{
		
	}
	
	public Bloger analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
			Bloger bloger = new Bloger();
			String str;
			try
			{
				Document doc = Jsoup.parse(content);
				
				// name
				Elements ename = doc.getElementsByClass("user_name");
				String name = ename.get(0).text();			
				System.out.println(name);
				
				// url
				String url =  ename.get(0).attr("href");
				System.out.println(url);
			
				// atricleAmt
				Element eaticle = doc.getElementById("blog_statistics").select("li").first();
				String articleAmt = eaticle.text();
				
				// visit
				Element evisit = doc.getElementById("blog_rank").select("li").first();
				String visits = evisit.text();
				
				bloger.setName(name);
				bloger.setUrl(url);
				bloger.setId(Converter.urlEncode(url));
				bloger.setHost(host);
				bloger.setArticleAmt(Converter.praseIntFromStr(articleAmt));
				bloger.setVisit(Converter.praseIntFromStr(visits));

							
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return bloger;
	}
	
	public Bloger analyze(CrawlURI curl)
	{
		// TODO Auto-generated method stub
		return analyze(curl.getHost(),curl.getEncode(),curl.getContent());
	}
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.csdn.net/a9529lty/article/details/7008537");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		System.out.println(curl.getContent());
		
		Bloger bloger = new CSDNBlogerAnalyzer().analyze(null, curl.getEncode(), curl.getContent());
		
		
	}
}
