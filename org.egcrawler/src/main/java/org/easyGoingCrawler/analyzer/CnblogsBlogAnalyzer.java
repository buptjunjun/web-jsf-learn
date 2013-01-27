package org.easyGoingCrawler.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CnblogsBlogAnalyzer implements Analyzer<Blog>
{
	private Pattern pattern = Pattern.compile("\\d+");
	private Pattern postDatePattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+\\s*\\d+:\\d+");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public CnblogsBlogAnalyzer()
	{
		
	}
	
	public Blog analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		String str;
		try
		{
			//System.out.println(str);
			Document doc = Jsoup.parse(content);
			String docText = doc.text();
			// name	
						
			// title
			Element etitle = doc.getElementsByTag("title").first();
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
			ecat = doc.getElementById("EntryTag");
			if(ecat != null)
			{
				Elements ecats = ecat.select("a");
				if(ecats != null)
					for(Element e:ecats) cats.add(e.text());
			}

			
			//post time
			Matcher mpost = this.postDatePattern.matcher(docText);
			boolean mpostDate = mpost.find();
			String postDateStr = mpost.group();
			int postdatePosition = mpost.end();
			
			int end = postdatePosition+80;
			if(end > docText.length())
				end = docText.length()-1;
				
			//visits and comments 
			String vandc = docText.substring(postdatePosition,end); 
			vandc = vandc.replaceFirst("([a-zA-z_-]+\\d+)|[\u4e00-\u9fa5]+\\d+", " ");
			
			//visit		
			Matcher m = pattern.matcher(vandc);
			int visits = -1; 
			int comments = -1;
			if(m.find())
			{
				 String tmp1 = m.group(0);
				 visits = Converter.praseIntFromStr(tmp1);
			}
			if(m.find())
			{
				 String tmp1 = m.group(0);
				 comments = Converter.praseIntFromStr(tmp1);
			}
			;
			// content 
			Element econtent = doc.getElementById("cnblogs_post_body");
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs.size();
			
			//blog.setBlogerURL(name);
			blog.setHost(host);
			blog.setContent(content);
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(comments);
			blog.setVisit(visits);
			blog.setTags(cats);
			blog.setTitle(title);
			Date postdate = formater.parse(postDateStr);
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
			HtmlElement e = ebody.getElementById("cnblogs_post_body");
			blog.setContent(e.asText());
//			WebDriver p = (WebDriver) curl.getReserve();
//			WebElement e = p.findElement(By.id("cnblogs_post_body"));
//			if ( e==null)
//			{
//				return null;
//			}
//			blog.setContent(e.getText());
			blog.setBlogerURL(this.getBlogerUrl(curl.getUrl()));
			blog.setUrl(curl.getUrl());
			blog.setId(Converter.urlEncode(curl.getUrl()));
			;
		
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		return blog;
	}
	
	public String getBlogerUrl(String url)
	{
		if(url == null) return null;
	    String[] split = url.split("archive");
	    if (split.length  > 1)
	    	return split[0];
	    return null;
	}
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://www.cnblogs.com/binb/archive/2013/01/03/xiangxiong_tencent.html");
		curl.setUrl("http://www.cnblogs.com/firstyi/archive/2007/04/26/728289.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.cnblogs.com");
		fetcher.fetch(curl);
		String content = null;
		
		content = curl.getContent();
		System.out.println(content);
//		try
//		{
//			FileWriter fout = new FileWriter(new File("51cto.html"));
//			fout.write(content);
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
		
		
//		Document doc = Jsoup.parse(content);
//		String text = doc.text();
//		System.out.println(text);
//		String p = "(随笔.{1,10}文章.{1,10}评论.{1,10})|(Posts.{1,10}Articles.{1,10}Comments.{1,10})";
//		String ap = "\\d\\d\\d\\d-\\d+-\\d+.*编辑";
//		Pattern pattern = Pattern.compile(p);
//		Matcher m = pattern.matcher(text);
//		boolean f = m.find();
//		String g = m.group();	
//		System.out.println("find = " + f +"   "+g);
//		
//		Pattern pattern1 = Pattern.compile(ap);
//		m = pattern1.matcher(text);
//		f = m.find();
//		g = m.group();
//		System.out.println("find = " +f+"  "+g);
		
		Blog blog = new CnblogsBlogAnalyzer().analyze(curl);
		System.out.println(blog+"");
		
//		String url = "http://www.cnblogs.com/v2m_/archive/2013/01/06/2846984.html";
//		String blogerUrl = new CnblogsBlogAnalyzer().getBlogerUrl(url);
//		System.out.println(blogerUrl);
	}



	
}
