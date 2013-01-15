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
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.AnalyzerUtil;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class A51ctoBlogAnalyzer implements Analyzer<Blog>
{	
	private Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d+-\\d+ \\d+:\\d+");
	private Pattern patternBrace = Pattern.compile("\\(\\s*\\d+\\s*\\)");
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public A51ctoBlogAnalyzer()
	{
		
	}
	
	public Blog analyze(String host,String encode, String content)
	{
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		String str;
		try
		{		
			Document doc = Jsoup.parse(content);
			// bloger url 
			Element eblogLink = doc.getElementsByClass("blogLink").select("a").first();
			String url = eblogLink.attr("href");
			
			Elements eshowTitle = doc.getElementsByClass("showTitle");
			// title
			String title = eshowTitle.text();
			
			// tag
			List<String> tags = new ArrayList<String>();
			Elements eshowTags = doc.getElementsByClass("showTags");
			if(eshowTags!=null)
			{
				Elements etags = eshowTags.select("a");
				if(etags!=null)
				{
					for(Element e:etags)
					{
						tags.add(e.text());
					}
				}
			}
			
			Elements estatic = doc.getElementsByClass("showType");
			String staticText = estatic.text();
			
			Matcher m1 = patternBrace.matcher(staticText);
			m1.find();
			m1.find();
			String visitsText = m1.group();
			m1.find();
			String visitsComment = m1.group();
			
			int visits = Converter.praseIntFromStr(visitsText);
			int comments = Converter.praseIntFromStr(visitsComment);
			
			//post time;
			Element eArtTime = doc.getElementsByClass("artTime").first();
			String postTimeStr = eArtTime.text();
			Date postTime = formater.parse(postTimeStr);
			
			// images
			Element eContent = doc.getElementsByClass("showContent").first();
			Elements eimages = eContent.select("img");
			int imgs = eimages.size();
			
			blog.setBlogerURL(url);
			blog.setHost(host);
			blog.setContent(content);
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(comments);
			blog.setVisit(visits);
			blog.setTags(tags);
			blog.setTitle(title);
			
			blog.setPostDate(postTime);
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
			
//			HtmlPage p = (HtmlPage) curl.getReserve();
//			HtmlElement ebody = p.getBody();
//			List<HtmlElement> e = ebody.getElementsByAttribute("div", "class", "showContent");
//			
			WebDriver p = (WebDriver) curl.getReserve();
			List<WebElement> e = p.findElements(ByClassName.className("showContent"));
			if ( e==null)
			{
				return null;
			}
			blog.setContent(e.get(0).getText());
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
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherByWebDriver",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
	//	curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
	//	curl.setUrl("http://jeray.blog.51cto.com/1649904/1095096");
		curl.setUrl("http://jeffrey2013.blog.51cto.com/1649904/800600");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		curl.setHost("blog.51cto.com");

		
//		AnalyzerUtil.persistObj(curl, "oschina.dat");
//		curl = (CrawlURI)AnalyzerUtil.readObj("oschina.dat");
		
		Blog blog = new A51ctoBlogAnalyzer().analyze(curl);
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
