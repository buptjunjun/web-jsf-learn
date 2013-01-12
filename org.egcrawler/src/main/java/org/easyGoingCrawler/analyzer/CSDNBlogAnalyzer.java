package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CSDNBlogAnalyzer implements Analyzer<Blog>
{
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public CSDNBlogAnalyzer()
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
			// name
			Elements ename = doc.getElementsByClass("user_name");
			String url =  ename.get(0).attr("href");
			
			// title
			Elements etitle = doc.getElementsByClass("link_title");
			String title = etitle.get(0).text();			
			blog.setTitle(title);
			
			// tags
			List<String> cats = new ArrayList<String>(0);
			Elements ecats = doc.getElementsByClass("link_categories");
			if(ecats!=null && ecats.size() >0)
			{	
				Elements tags = ecats.get(0).select("a");				
				for(Element e:tags)
					cats.add(e.text());
			}
			
			// visits			
			Elements evisit = doc.getElementsByClass("link_view");
			String visit = evisit.get(0).text();			
				
			// comments
			Elements ecomments = doc.getElementsByClass("link_comments");
			String comments = ecomments.text();
	
			// content
			Element econtent = doc.getElementById("article_content");
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs.size();
			
			// post date 
			Elements epostDate = doc.getElementsByClass("link_postdate");
			String postDate = epostDate.text();
			
			blog.setBlogerURL(url);
			blog.setHost(host);
			blog.setContent(null);
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(Converter.praseIntFromStr(comments));
			blog.setVisit(Converter.praseIntFromStr(visit));
			blog.setTags(cats);
			
			Date post = formater.parse(postDate);
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
			HtmlElement e = p.getElementById("article_content");
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
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.csdn.net/s98/article/details/7307500");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);		
		System.out.println(curl.getContent());	
		
		
		Blog blog = new CSDNBlogAnalyzer().analyze(curl);
		
		System.out.println(blog);
		 	
	}
	
}
