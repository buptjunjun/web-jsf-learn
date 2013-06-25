package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Movie;
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

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DoubanMovieAnalyzer implements Analyzer<Movie>
{	
	private Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+ \\d+:\\d+");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public DoubanMovieAnalyzer()
	{
		
	}
	
	public Movie analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Movie movie = new Movie();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);
			String name = doc.title();
			Elements directors = doc.getElementsMatchingOwnText("导演");
			if(directors != null && !directors.isEmpty())
			{
				Element director = directors.first();
				Element e = director.nextElementSibling();
				movie.setDirector(e.text());
				
			}
			
			Elements actors = doc.getElementsMatchingOwnText("导演");
			if(directors != null && !directors.isEmpty())
			{
				Element director = directors.first();
				Element e = director.nextElementSibling();
				movie.setDirector(e.text());
				
			}
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return movie;
	}
	
	public Movie analyze(CrawlURI curl)
	{
		Movie movie = null;
		try
		{
			movie = this.analyze(curl.getHost(),curl.getEncode(),curl.getContent());
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		return movie;
		
	}
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
	//	curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://movie.douban.com/subject/2977731/?from=subject-page");
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
		Movie blog = new DoubanMovieAnalyzer().analyze(curl);
		System.out.println(blog);

		//		Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+ \\d+:\\d+");
//		String test= "windows2003 定时重启服务器 (2012-12-26 08:51)";
//		Matcher m = pattern.matcher(test);
//		Boolean b = m.find();
//		String postDate = m.group(0);
//		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Date post=null;
//		try
//		{
//			 post = formater.parse(postDate);
//		} catch (ParseException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		System.out.println(post);
//		 	
	}
	
	
}
