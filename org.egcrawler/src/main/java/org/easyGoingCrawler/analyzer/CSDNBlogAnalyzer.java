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

public class CSDNBlogAnalyzer implements Analyzer<Blog>
{
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public CSDNBlogAnalyzer()
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
			Document doc = Jsoup.parse(str);
			// name
			Elements ename = doc.getElementsByClass("user_name");
			String url =  ename.get(0).attr("href");
			
			// title
			Elements etitle = doc.getElementsByClass("link_title");
			String title = etitle.get(0).text();			
			
			
			// tags
			Elements ecats = doc.getElementsByClass("link_categories").get(0).select("a");
			List<String> cats = new ArrayList<String>(0);
			for(Element e:ecats) cats.add(e.text());
			
			
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
			blog.setContent(content);
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
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.csdn.net/a9529lty/article/details/7008537");
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
		
	//	Blog blog = new CSDNBlogAnalyzer().analyze(null, "utf-8", curl.getContent());
		
		
		 	
	}
	
}
