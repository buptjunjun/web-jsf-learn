package org.easyGoingCrawler.analyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CnblogsBlogAnalyzer implements Analyzer<Blog>
{
	private Pattern pattern = Pattern.compile("\\(\\d+\\)");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public CnblogsBlogAnalyzer()
	{
		
	}
	
	public Blog analyze(String host,String encode,byte[] content)
	{
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		String str;
		try
		{
			str = new String(content,encode);	
			//System.out.println(str);
			Document doc = Jsoup.parse(str);
			// name
			Elements ename = doc.getElementsByClass("postDesc").select("a");
			String name = ename.get(0).attr("href");	
						
			// title
			Element etitle = doc.getElementById("cb_post_title_url");
			String title = etitle.text();			
			
			
			// tags
			List<String> cats = new ArrayList<String>(0);
			Element ecat = doc.getElementById("BlogPostCategory");
			if(ecat != null)
			{
				Elements ecats = ecat.select("a");
				if(ecats != null)
					for(Element e:ecats) cats.add(e.text());
			}

			
			// time
			Element edate = doc.getElementById("post-date");
			String datestr	 = edate.text();
			
			//comment
			Element ecomment = doc.getElementById("post-comment-count");
			String comment	 = ecomment.text();
			
			//visit
			Element epostDesc = doc.getElementsByClass("postDesc").first();
			String tmp	 = epostDesc.text();
			Matcher m = pattern.matcher(tmp);
			int visits = 0; 
			if(m.find())
			{
				 String tmp1 = m.group(0);
				 visits = Converter.praseIntFromStr(tmp1);
			}
			

			
			// content
			Element econtent = doc.getElementById("cnblogs_post_body");
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs.size();
			
			blog.setBlogerURL(name);
			blog.setHost(host);
			blog.setContent(content);
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(Converter.praseIntFromStr(comment));
			blog.setVisit(visits);
			blog.setTags(cats);
			
			Date postdate = formater.parse(datestr);
			blog.setPostDate(postdate);
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
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.cnblogs.com/xinz/archive/2012/11/23/2785098.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);

		try
		{
			System.out.println(new String (curl.getContent(),curl.getEncode()));
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Blog blog = new CnblogsBlogAnalyzer().analyze(null, curl.getEncode(), curl.getContent());
//		System.out.println(blog+"");
		
		
	}
	
}
