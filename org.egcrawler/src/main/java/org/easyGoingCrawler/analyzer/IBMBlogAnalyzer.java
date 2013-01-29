package org.easyGoingCrawler.analyzer;

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

public class IBMBlogAnalyzer implements Analyzer<Blog>
{
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private Pattern patternPostTime = Pattern.compile("\\d\\d\\d\\d\\s*年\\s*\\d+\\s*月\\s*\\d+\\s*日");
	public IBMBlogAnalyzer()
	{
		
	}
	
	public Blog analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		try
		{
			//encode = "gb2312";	
			Document doc = Jsoup.parse(content);
			//判断是否是 需要的blog
			Element summary = doc.getElementById("dw-summary-article");
			if( summary == null )return null;
			Element  authorpopup = summary.getElementById("authortip1");
			if(authorpopup == null) return null;
			Element articleRating = summary.getElementById("art-rating-summary");
			if( articleRating == null)return null;
			
			// url of Bloger
			Elements ename = summary.getElementsByClass("dwauthor");
			String url =  ename.get(0).text();
			
			// title
			Elements etitle = summary.getElementsByClass("dw-content-head");
			String title = etitle.get(0).text();			
			
			
			// tags	is empty for ibm 	
			List<String> cats = new ArrayList<String>(0);
			
			// visits			
			Elements statics = summary.getElementsByClass("leading");
			String staticsText = statics.get(0).text();			
			String visitsText = staticsText.split("访问情况")[1];
			int visits = Converter.praseIntFromStr(visitsText);
			
			// post date
			Matcher m = this.patternPostTime.matcher(staticsText);
			boolean postb = m.find();
			String postStr = m.group();
			postStr = postStr.replaceAll(" ", "");
			postStr = postStr.replace("年", "-");
			postStr = postStr.replace("月", "-");
			postStr = postStr.replace("月", "-");
			Date postTime = formater.parse(postStr);
			
			Element comment = summary.getElementById("nCmts");
			String comments = comment.text();
			// content
			Element econtent = doc.getElementById("ibm-content-body");
			// amount of pictures
			Elements eimgs = econtent.select("img");
			int imgs = eimgs.size();		;
			// post date 
			Elements epostDate = doc.getElementsByClass("link_postdate");
			String postDate = epostDate.text();
			
			
			blog.setBlogerURL(url); // set as the name of author
			blog.setHost(host);
			
			blog.setEncode(encode);
			blog.setPictures(imgs);
			blog.setComment(-1);// comments set to -1
			blog.setVisit(visits);
			blog.setTags(cats);
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
	{		Blog blog = null;
	try
	{
		blog = analyze(curl.getHost(),curl.getEncode(),curl.getContent());
		if(blog == null)
			return null;
		
		HtmlPage p = (HtmlPage) curl.getReserve();
		HtmlElement ebody = p.getBody();
		// content
		HtmlElement e = ebody.getElementById("ibm-content-body");
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
		curl.setUrl("http://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/index.html");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		
//		AnalyzerUtil.persistObj(curl, "ibm.dat");
//		
//		curl = (CrawlURI) AnalyzerUtil.readObj("ibm.dat");
		
		//	System.out.println(curl.getContent());

		Blog blog = new IBMBlogAnalyzer().analyze(curl);
		
		System.out.println(blog);
		 	
	}


}
