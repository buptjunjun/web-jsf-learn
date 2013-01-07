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

public class A51ctoBlogerAnalyzer implements Analyzer<Bloger>
{
	private Pattern pattern = Pattern.compile("\\(\\d+\\)");
	public A51ctoBlogerAnalyzer()
	{
		
	}
	
	public Bloger analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
			Bloger bloger = new Bloger();
			try
			{		
				Document doc = Jsoup.parse(content);
				// bloger url 使用 用户名title
				Element eTitle = doc.getElementsByClass("title").first();
				String name = eTitle.text();
				name = name.split(" ")[0];
				
				// url
				Element eblogLink = doc.getElementsByClass("blogLink").select("a").first();
				String url = eblogLink.attr("href");
				
				// 统计
				Element eStatic = doc.select("span[class=infoListHead]").first();
				String staticText = eStatic.parent().text();
			
				String wenzhang = staticText.split("文章数")[1];	
				int articleAmt = Converter.praseIntFromStr(wenzhang);
				
				String visitText = staticText.split("访问量")[1];
				int visits = Converter.praseIntFromStr(visitText);
				
				bloger.setName(name);
				bloger.setUrl(url);
				bloger.setId(Converter.urlEncode(url));
				bloger.setHost(host);
				bloger.setArticleAmt(articleAmt);
				bloger.setVisit(visits);

							
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
		curl.setUrl("http://woshixy.blog.51cto.com/5637578/1107054");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		curl.setHost("blog.51cto.com");
		Bloger bloger = new A51ctoBlogerAnalyzer().analyze(curl);
		System.out.println(bloger);
		
	}
}
