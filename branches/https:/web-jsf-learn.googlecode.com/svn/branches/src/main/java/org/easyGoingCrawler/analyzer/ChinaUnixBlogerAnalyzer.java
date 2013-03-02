package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
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


public class ChinaUnixBlogerAnalyzer implements Analyzer<Bloger>
{
	private Pattern pattern= Pattern.compile("\\d+");
	public ChinaUnixBlogerAnalyzer()
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
				Element eprofile = doc.getElementsByClass("Blog_left1_1").first();
				Elements eas = eprofile.select("a[href]");
				String url = "http://blog.chinaunix.net"+ eas.get(1).attr("href");
				
				// name
				String name = eas.get(1).text();
			
				// atricleAmt
				Element estatics = doc.getElementsByClass("Blog_ul1").first();
				String estaticstr = estatics.text();
				
				// visit and comments
				Matcher m = pattern.matcher(estaticstr);				
				m.find();
				String visits = m.group();
				m.find();
				String  articleAmt = m.group();
				
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
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.chinaunix.net/uid-26893610-id-3454035.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("blog.chinaunix.net");
		fetcher.fetch(curl);
		//System.out.println(new String (curl.getContent()));
		//curl = (CrawlURI)AnalyzerUtil.readObj("chinaunix.dat");
		Bloger bloger = new ChinaUnixBlogerAnalyzer().analyze(curl);
		System.out.println(bloger);
		
	}

}
