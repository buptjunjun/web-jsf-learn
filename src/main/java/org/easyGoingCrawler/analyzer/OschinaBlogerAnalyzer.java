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

public class OschinaBlogerAnalyzer implements Analyzer<Bloger>
{
	private Pattern pattern = Pattern.compile("\\(\\d+\\)");
	public OschinaBlogerAnalyzer()
	{
		
	}
	
	public Bloger analyze(String host, String encode, String content)
	{
		// TODO Auto-generated method stub
			Bloger bloger = new Bloger();
			try
			{
				Document doc = Jsoup.parse(content);
				
				Element spaceLeft = doc.getElementById("SpaceLeft");
				// name
				Elements ename = spaceLeft.select("a");
				String url =  ename.get(0).attr("href");
								
				// url
				String name =  ename.get(1).text();
			
				Elements stats = doc.getElementById("Stat").select("li");
				// atricleAmt
				Element evisit = stats.last();
				String visits = evisit.text();
				
				// visit
				Elements earticles = doc.getElementById("BlogCatalogs").select("li");
				int articleAmt = 0;
				if(earticles != null)
				{
					for(Element e: earticles)
					{
						String cat = e.text();
						int num = Converter.praseIntFromStr(cat);
						articleAmt+=num;
					}
				}
				
				bloger.setName(name);
				bloger.setUrl(url);
				bloger.setId(Converter.urlEncode(url));
				bloger.setHost(host);
				bloger.setArticleAmt(articleAmt);
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
		curl.setUrl("http://my.oschina.net/u/616092/blog/99399");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		curl.setHost("my.oschina.net");

		
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
		Bloger bloger = new OschinaBlogerAnalyzer().analyze(curl);
		System.out.println(bloger);
		
	}


}
