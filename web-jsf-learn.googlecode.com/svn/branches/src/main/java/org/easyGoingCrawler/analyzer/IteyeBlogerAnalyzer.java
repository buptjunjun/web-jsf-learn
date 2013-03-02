package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.AnalyzerUtil;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IteyeBlogerAnalyzer implements Analyzer<Bloger>
{
	private Pattern pattern = Pattern.compile("\\(\\d+\\)");
	public IteyeBlogerAnalyzer()
	{
		
	}
	
	public Bloger analyze(String host, String encode, String content)
	{
		// TODO Auto-generated method stub
			Bloger bloger = new Bloger();
			try
			{
				Document doc = Jsoup.parse(content);
				
				Element elogo = doc.getElementById("blog_owner_logo");
				// url
				Elements enname = elogo.select("a");
				String url =  enname.get(0).attr("href");
				
				
				// name
				Element ename = doc.getElementById("blog_owner_name");
				String name =  ename.text();
			
				Elements stats = doc.getElementById("blog_actions").select("li");
				
				// visits
				Element evisit = stats.first();
				String visits = evisit.text();
				
				// article Amount
				Element earticles = doc.getElementById("blog_menu").select("li").first();
				String articleAmt = earticles.text();
				
				
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
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
	//	curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://gaopenghigh.iteye.com/blog/1716658");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		curl.setHost("www.iteye.com/blogs");

		
//		AnalyzerUtil.persistObj(curl, "oschina.dat");
//		curl = (CrawlURI)AnalyzerUtil.readObj("oschina.dat");
		
//		try
//		{
//			System.out.println(curl);
//			String tmp = new String (curl.getContent(),curl.getEncode());
//			
//			System.out.println(tmp);
//		} catch (UnsupportedEncodingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Bloger bloger = new IteyeBlogerAnalyzer().analyze(curl);
		System.out.println(bloger);
		
	}


}
