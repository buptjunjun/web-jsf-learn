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

public class CnblogsBlogerAnalyzer implements Analyzer<Bloger>
{
	private Pattern pattern = Pattern.compile("\\d+");
	public CnblogsBlogerAnalyzer()
	{
		
	}
	
	public Bloger analyze(String host,String encode,byte[] content)
	{
		// TODO Auto-generated method stub
			Bloger bloger = new Bloger();
			String str;
			try
			{
				str = new String(content,encode);		
				Document doc = Jsoup.parse(str);
				
				// name
				Elements ename = doc.getElementById("profile_block").select("a");
				String name = ename.get(0).text();			
				System.out.println(name);
				
				// url
				String url =  ename.get(0).attr("href");
				System.out.println(url);
			
				// atricleAmt
				Element earticle = doc.getElementsByClass("blogStats").first();
				int artAmt = 0;
				String tmp = earticle.text();
				Matcher m = this.pattern.matcher(tmp);				
				String suibi = m.group(0);
				String wenzhang =m.group(1);
				
				// visit
				Element evisit = doc.getElementById("blog_rank").select("li").first();
				String visits = evisit.text();
				
				bloger.setName(name);
				bloger.setUrl(url);
				bloger.setId(Converter.urlEncode(url));
				bloger.setHost(host);
				bloger.setArticleAmt(artAmt);
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
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.csdn.net/a9529lty/article/details/7008537");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		System.out.println(new String (curl.getContent()));
		
		Bloger bloger = new CnblogsBlogerAnalyzer().analyze(null, "utf-8", curl.getContent());
		
		
	}
}
