package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

public class IBMBlogerAnalyzer implements Analyzer<Bloger>
{
	public IBMBlogerAnalyzer()
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
				//判断是否是 需要的blog
				Element summary = doc.getElementById("dw-summary-article");
				Element  authorpopup = summary.getElementById("authortip1");
				Element articleRating = summary.getElementById("art-rating-summary");
				
				if( summary == null || authorpopup == null || articleRating == null)
					return null;
				
				// url of Bloger
				Elements ename = summary.getElementsByClass("dwauthor");
				String url =  ename.get(0).text();
				
				bloger.setName(url);
				bloger.setUrl(url);
				bloger.setId(Converter.urlEncode(url));
				bloger.setHost(host);
				bloger.setArticleAmt(-1);
				bloger.setVisit(-1);
							
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return bloger;
	}
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("https://www.ibm.com/developerworks/cn/linux/l-cn-kdump3/");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		
			System.out.println(curl.getContent());
		
//		curl = (CrawlURI) AnalyzerUtil.readObj("ibm.dat");
		Bloger bloger = new IBMBlogerAnalyzer().analyze(curl);
		System.out.println(bloger);
		
	}


	public Bloger analyze(CrawlURI curl)
	{
		// TODO Auto-generated method stub
		return analyze(curl.getHost(),curl.getEncode(),curl.getContent());
	}
}
