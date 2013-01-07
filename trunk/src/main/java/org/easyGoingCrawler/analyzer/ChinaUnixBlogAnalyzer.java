package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
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

public class ChinaUnixBlogAnalyzer implements Analyzer<Blog>
{	
	private Pattern patternRead = Pattern.compile("\\(\\s*\\d+\\s*\\)");
	private Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+ \\d+:\\d+");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public ChinaUnixBlogAnalyzer()
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
			Element eprofile = doc.getElementById("profile");
			Elements eas = eprofile.select("a[href]");
			String url = "http://blog.chinaunix.net"+ eas.get(1).attr("href");
			
			// title
			Element ecenter = doc.getElementById("blog_center");
			Elements etitles = ecenter.getElementsByClass("tit6");
			Element etitle = etitles.select("a").first();
			String title = etitle.text();
			
			// post date 
			String postDate = etitles.text();
			Matcher m = pattern.matcher(postDate);
			Boolean b = m.find();
			postDate = m.group(0);
			Date post = formater.parse(postDate);
			blog.setPostDate(post);
			blog.setCrawledDate(new Date());
			
			// tags
			List<String> cats = new ArrayList<String>(0);
			Elements tmp = ecenter.getElementsByClass("tit7");
			if(tmp!=null && tmp.size() > 0)
			{	
				Elements ecats = tmp.select("a");	
				if(ecats!=null && ecats.size()>0)
				{
					for(int i = 1 ; i < ecats.size(); i++)
					{
						Element e = ecats.get(i);
						cats.add(e.text());
					}
				}
			}
			
			// visits			
			Elements evisit = doc.getElementsByClass("read");
			String visit = evisit.get(0).text();	
			Matcher m1 =patternRead.matcher(visit);
			m1.find();

			String visitstr = m1.group(0);
			int visits = Converter.praseIntFromStr(visitstr);

			// comments
			int comments =0;
			if( m1.find())
			{
				String commentstr = m1.group();
				comments = Converter.praseIntFromStr(commentstr);
			}
	
			// content
			Element econtent = doc.getElementById("detail");
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs == null ?0 :eimgs.size();
			
			
			
			blog.setBlogerURL(url);
			blog.setHost(host);
			blog.setContent(content);
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(comments);
			blog.setVisit(visits);
			blog.setTags(cats);
			

			
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
			HtmlElement e = p.getElementById("detail");
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
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
	//	curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://blog.chinaunix.net/uid-22788794-id-3450811.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		curl.setHost("blog.chinaunix.net");
//		try
//		{
//			String tmp = new String (curl.getContent(),"gbk");
//			Charset charset = java.nio.charset.Charset.forName("gbk");
//			byte[] b = tmp.getBytes("gbk");
//			System.out.println(new String(b,"utf-8"));
//
//			System.out.println();
//		} catch (UnsupportedEncodingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		//AnalyzerUtil.persistObj(curl, "chinaunix.dat");
//		curl = (CrawlURI)AnalyzerUtil.readObj("chinaunix.dat");
//		
		Blog blog = new ChinaUnixBlogAnalyzer().analyze(null, "utf-8", curl.getContent());
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
