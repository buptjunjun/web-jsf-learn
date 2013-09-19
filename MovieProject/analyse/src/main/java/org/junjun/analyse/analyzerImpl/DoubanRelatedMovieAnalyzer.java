package org.junjun.analyse.analyzerImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junjun.analyse.analyzer.Analyzer;
import org.junjun.analyse.analyzer.bean.BRelatedMovies;
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.analyse.dao.mongo.MongoDAOapi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DoubanRelatedMovieAnalyzer implements Analyzer<BRelatedMovies>
{	
	public static String host="movie.douban.com";
	
	 private Logger logger = Logger.getLogger(DoubanRelatedMovieAnalyzer.class);
	 private static final int HTMLERROR= 2; 
	 DAOapi dao = null;
	public DoubanRelatedMovieAnalyzer()
	{
		 dao = new MongoDAOapi();
	}
	
	public BRelatedMovies analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		BRelatedMovies rmovie = new BRelatedMovies();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);

	//		System.out.println(str);			
			Element infoElem = doc.getElementsByClass("recommendations-bd").first();		
			List<Node> infoNodes = infoElem.childNodes();	
			List<String> urls = new ArrayList<String>();
			for(Node n:infoNodes)
			{
				if(Element.class.isInstance(n))
				{
					try
					{				
						//System.out.println(n);
						Element node = (Element)n;
						String url = node.select("a[href]").first().attr("href");
						url = url.replace("/?from=subject-page", "");
						url = url.replace("http://movie.douban.com/subject/", "");
						//System.out.println(url);
						urls.add(url);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}				
				}
			}
			
			rmovie.setUrls(urls);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			
			if(rmovie.getUrls() == null || rmovie.getUrls().size()==0)
				return rmovie;
			else
				return null;
		}
		
		return rmovie;
	}
	

	public BRelatedMovies analyze(Html html)
	{
		BRelatedMovies rmovie = this.analyze(html.getHost(), html.getEncode(), html.getHtml());
		if(rmovie==null)
			return null;
		rmovie.setId(html.getId());
		return rmovie;
	}
	
	public void analyse()
	{		
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		Html initHtml = new Html();
		initHtml.setHost("movie.douban.com");
		Date date = new Date();
		date.setYear(10);
		int limit = 50;
		initHtml.setCrawledDate(date);	
		List<Html> htmls = dao.getNextHtmls(initHtml,limit);
		int count = 0;
		Set<String> updateField = new HashSet<String>();
		updateField.add("magicNum");
		while(htmls!=null)
		{
			Html store = null;
			for(Html html:htmls)
			{
				logger.info("html:"+(count++)+html);
				System.out.println("html:"+(count++)+html);
				BRelatedMovies rmovie = this.analyze(html);	
				
				if(rmovie!=null)
				{
					dao.insertObj(rmovie);
					logger.info("movie:"+(count)+rmovie);
					System.out.println("movie:"+(count)+rmovie);
				}
				else
				{
				
					html.setMagicNum(HTMLERROR);
					dao.updateHtml(html, updateField);
					logger.info("html:"+(count)+"update "+html);
				}
				
				if(html.getCrawledDate()!=null)
					store = html;
			}
			
			htmls = dao.getNextHtmls(store,limit);
		}
	}
	
	public void test(String url)
	{
		String id = Converter.urlEncode(url);
		Html html  = dao.getHtml(id);
		System.out.println(html.getHtml());
		BRelatedMovies m = this.analyze(html);
		System.out.println(m);
	}
	static public void main(String [] args)
	{
		DoubanRelatedMovieAnalyzer douban = new DoubanRelatedMovieAnalyzer();
		//douban.test("http://movie.douban.com/subject/1291832");
		//douban.test("http://movie.douban.com/subject/23055587");
		//douban.test("http://movie.douban.com/subject/1950253");
		//douban.test("http://movie.douban.com/subject/20425881");
		// test date
		//douban.test("http://movie.douban.com/subject/21354055");
		// test analyzer fail
		//douban.test("http://movie.douban.com/subject/5284554");
		douban.analyse();
	}

	public BRelatedMovies analyze(Object obj)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
