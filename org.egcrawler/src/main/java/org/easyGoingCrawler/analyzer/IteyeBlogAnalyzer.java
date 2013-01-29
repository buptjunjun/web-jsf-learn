package org.easyGoingCrawler.analyzer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Blog;
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

public class IteyeBlogAnalyzer implements Analyzer<Blog>
{	
	private Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+ \\d+:\\d+");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public IteyeBlogAnalyzer()
	{
		
	}
	
	public Blog analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);
			
			Element elogo = doc.getElementById("blog_owner_logo");
			// url
			Elements enname = elogo.select("a");
			String url =  enname.get(0).attr("href");
					
			// title
			String title = doc.getElementsByClass("blog_title").select("a").first().text();
			blog.setTitle(title);
			
			// tags
			List<String> cats = new ArrayList<String>(0);
			Elements ecats = doc.getElementsByClass("blog_categories");
			if(ecats !=null && ecats.size()>=1)
			{
				Elements catagaries = ecats.first().select("li");
				for(Element e :catagaries)
				{
					cats.add(e.text());
				}
			}					
			ecats = doc.getElementsByClass("news_tag");
			if(ecats !=null && ecats.size()>=1)
			{
				Elements catagaries = ecats.first().select("a");
				for(Element e :catagaries)
				{
					cats.add(e.text());
				}
			}
			
			
			Elements statistics = doc.getElementsByClass("blog_bottom").select("li");
			
			// post date 
			Element epostDate = statistics.get(0);
			String postDate = epostDate.text();
			
			// visits			
			Element evisit = statistics.get(1);
			String visit = evisit.text();			
				
			// comments
			Element ecomments = statistics.get(2);
			String comments = ecomments.text();
	
			// content
			Element econtent = doc.getElementById("blog_content");
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs.size();
			
			
			
			Matcher m = pattern.matcher(postDate);
			Boolean b = m.find();
			Date post = new Date();
			if(b == true)
			{
				postDate = m.group();
				post = formater.parse(postDate);
			}
			else 
			{
				// 比如 "6小时前"
				post = new Date();			
			}
			
			blog.setBlogerURL(url);
			blog.setHost(host);
			blog.setContent(content);
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(Converter.praseIntFromStr(comments));
			blog.setVisit(Converter.praseIntFromStr(visit));
			blog.setTags(cats);
			
			
			blog.setPostDate(post);
			blog.setCrawledDate(new Date());
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return blog;
	}
	
	public Blog analyze(CrawlURI curl)
	{
		Blog blog = null;
		try
		{
			blog = analyze(curl.getHost(),curl.getEncode(),curl.getContent());
			if(blog == null)
				return null;
			
			HtmlPage p = (HtmlPage) curl.getReserve();
			HtmlElement ebody = p.getBody();
			HtmlElement e = ebody.getElementById("blog_content"	);
			if ( e==null)
			{
				return null;
			}
			blog.setContent(e.asText());
			blog.setUrl(curl.getUrl());
			blog.setId(Converter.urlEncode(curl.getUrl()));
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		return blog;
		
	}
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
	//	curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://doppp.iteye.com/blog/1777150");
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
		Blog blog = new IteyeBlogAnalyzer().analyze(curl);
		FileOutputStream fo = null;
		try
		{
		 fo = new FileOutputStream("result.txt");
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(blog);
		PrintWriter p = new PrintWriter(fo);
		
		p.println(blog);
		p.close();
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
