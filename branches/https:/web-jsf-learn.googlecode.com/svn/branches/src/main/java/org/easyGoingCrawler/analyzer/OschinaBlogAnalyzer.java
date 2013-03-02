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

public class OschinaBlogAnalyzer implements Analyzer<Blog>
{	
	private Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+ \\d+:\\d+");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public OschinaBlogAnalyzer()
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
			
			Element spaceLeft = doc.getElementById("SpaceLeft");
			// name
			Elements ename = spaceLeft.select("a");
			String url =  ename.get(0).attr("href");
			
			Element blogentity = doc.getElementsByClass("BlogEntity").first();
			// title
			Elements etitle = blogentity.getElementsByTag("h1");
			String title = etitle.get(0).text();		
			blog.setTitle(title);
			
			// tags
			Element ecats = doc.getElementsByClass("NavPath").get(0).select("a").get(2);
			List<String> cats = new ArrayList<String>(0);
			String tag = ecats.text();
			cats.add(tag);
			
			Elements tagbody = doc.getElementsByClass("BlogTags");
			if( tagbody != null && tagbody.size() > 0)
			{
				Element element = tagbody.first();
				Elements tags = element.select("a");
				if (tags != null && tags.size()>0)
				{
					for(Element e : tags)
					{
						String text = e.text();
						cats.add(text);
					}
				}
			}
			
			Elements admin = blogentity.getElementsByTag("strong");
			// visits			
			Element evisit = admin.get(0);
			String visit = evisit.text();			
				
			// comments
			Element ecomments =admin.get(1);
			String comments = ecomments.text();
	
			// content
			Element econtent = doc.getElementsByClass("BlogContent").first();
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs.size();
			
			// post date 
			Element epostDate = doc.getElementsByClass("BlogStat").get(0);
			String postDate = epostDate.text();
			
			Matcher m = pattern.matcher(postDate);
			Boolean b = m.find();
			postDate = m.group();

			
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
			List<HtmlElement> e = ebody.getElementsByAttribute("div", "class", "BlogContent");
			if ( e==null)
			{
				return null;
			}
			blog.setContent(e.get(0).asText());
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
		curl.setUrl("http://my.oschina.net/huxuanhui/blog/106136");
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
		Blog blog = new OschinaBlogAnalyzer().analyze(curl);
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
