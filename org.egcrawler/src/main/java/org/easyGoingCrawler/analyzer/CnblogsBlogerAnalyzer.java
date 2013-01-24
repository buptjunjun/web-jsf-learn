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
	private String posts = "(Ëæ±Ê)|([P|p]osts)";
	private Pattern patternPosts = Pattern.compile(posts);
	public CnblogsBlogerAnalyzer()
	{
		
	}
	
	public Bloger analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
			Bloger bloger = new Bloger();
			try
			{	
				Document doc = Jsoup.parse(content);
				String docText = doc.text();
				Matcher m = this.patternPosts.matcher(docText);
				m.find();
				int postsPosition = m.end();
				
				String postsStr = docText.substring(postsPosition,postsPosition+80);
				
				String postsStr1 = docText.substring(postsPosition,postsPosition+30);
				
				Matcher mpost = pattern.matcher(postsStr);
				
				mpost.find();
				String suibiStr = mpost.group();
				mpost.find();
				String wenzhangStr = mpost.group();
				
				//articles
				int artAmt = Converter.praseIntFromStr(suibiStr) +Converter.praseIntFromStr(wenzhangStr);
				
				//comments replace the visits
				mpost.find();
				String comments = mpost.group();
				int commnets = Converter.praseIntFromStr(comments);
//				// name
//				Elements ename = doc.getElementById("profile_block").select("a");
//				String name = ename.get(0).text();			
//				System.out.println(name);
//				
//				// url
//				String url =  ename.get(0).attr("href");
//				System.out.println(url);
			
				// atricleAmt

				
				// visit
				
//				bloger.setName(name);
//				bloger.setUrl(url);
//				bloger.setId(Converter.urlEncode(url));
				bloger.setHost(host);
				bloger.setArticleAmt(artAmt);
				bloger.setVisit(commnets);

							
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return bloger;
			}
			
			return bloger;
	}
	
	public Bloger analyze(CrawlURI curl)
	{
		// TODO Auto-generated method stub
		Bloger bloger = this.analyze(curl.getHost(), curl.getEncode(), curl.getContent());
		try
		{
			String blogerUrl = new CnblogsBlogAnalyzer().getBlogerUrl(curl.getUrl());
			bloger.setUrl(blogerUrl);
			bloger.setId(Converter.urlEncode(blogerUrl));
			bloger.setName(blogerUrl);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return bloger; 
	}
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherByWebDriver",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		//curl.setUrl("http://www.cnblogs.com/binb/archive/2013/01/03/xiangxiong_tencent.html");
		curl.setUrl("http://www.cnblogs.com/lzcarl/archive/2006/08/24/485604.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.cnblogs.com");
		fetcher.fetch(curl);
		String content = null;

		content = curl.getContent();
		//System.out.println(content);
		
		
//		Document doc = Jsoup.parse(content);
//		String text = doc.text();
//		System.out.println(text);
//		String p = "(Ëæ±Ê.{1,10}ÎÄÕÂ.{1,10}ÆÀÂÛ.{1,10})|(Posts.{1,10}Articles.{1,10}Comments.{1,10})";
//		String ap = "\\d\\d\\d\\d-\\d+-\\d+.*±à¼­";
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
		
		Bloger blog = new CnblogsBlogerAnalyzer().analyze(curl);
		System.out.println(blog+"");
		
	}

	
}
