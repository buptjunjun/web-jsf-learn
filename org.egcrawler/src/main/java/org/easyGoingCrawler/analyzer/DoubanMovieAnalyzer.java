package org.easyGoingCrawler.analyzer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.AnalyzerUtil;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DoubanMovieAnalyzer implements Analyzer<Movie>
{	
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private Pattern patternInt = Pattern.compile("\\d+");
	private Pattern patternURL = Pattern.compile("http://movie.douban.com/subject/\\d+/");

	 static String loc = "制片国家/地区: ";
	 static String language = "语言:";
	 static String date = "上映日期:"; 
	 static String length = "片长:";
	 static String aname = "又名:";
	 static String imdb = "IMDb";
	 static int lengthLoc = loc.length();
	 static int lengthDate = date.length(); 
	 static int lengthLength = length.length();
	 static int lengthAname = aname.length();
	 
	public DoubanMovieAnalyzer()
	{
		
	}
	
	public Movie analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Movie movie = new Movie();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);
			str = doc.text();
			
			//片名
			String name = doc.title();
			name = name.replace("(豆瓣)", "");
			movie.setName(name);
			
			Elements directors = doc.getElementsMatchingOwnText("导演");
			if(directors != null && !directors.isEmpty())
			{
				Element director = directors.first();
				Element e = director.nextElementSibling();
				movie.setDirector(e.text());
				
			}
			
			Elements actors = doc.getElementsMatchingOwnText("主演");
			List<String> actorList = new ArrayList<String>();
			if(actors != null && !actors.isEmpty())
			{
				Element e = actors.first();
				e = e.nextElementSibling();
				do
				{
					actorList.add(e.text());
					e = e.nextElementSibling();
				}
				while(e!=null);
				movie.setActors(actorList);
			}
			
			Elements types = doc.getElementsMatchingOwnText("类型");
			List<String> typeList = new ArrayList<String>();
			if(types != null && !types.isEmpty())
			{
				Element e = types.first();
				e = e.nextElementSibling();
				do
				{
					typeList.add(e.text());
					e = e.nextElementSibling();
					if(e!=null && e.tagName().equals("br")) break;
				}
				while(e!=null);
				movie.setType(typeList);
			}
			
			// 国家
			int start = str.indexOf(loc);
			int end = str.indexOf(language);
			String location = str.substring(start+lengthLoc, end);
			movie.setLocation(location);

			// 日期
			start = str.indexOf(date);
			end = str.indexOf(length);
			String date1 = str.substring(start+lengthDate,end);
			Date date = formater.parse(date1);
			movie.setDate(date);
			
			// 片长
			start = str.indexOf(length);
			end = str.indexOf(aname);
			String length = str.substring(start+lengthLength,end);		
			Matcher m = patternInt.matcher(length);
			m.find();
			length = m.group();
			int len = Integer.parseInt(length);
			movie.setTimespan(len);
			
			// 又名
			start = str.indexOf(aname);
			end = str.indexOf(imdb);
			if(start > 0 && end > 0)
			{
				String anotherName = str.substring(start+lengthAname,end);
				String [] anotherNames = anotherName.split("/");
				if (anotherNames != null)
				{
					movie.setAnotherName(Arrays.asList(anotherNames));
				}
			}
		
			//分数
			Elements scoreEle = doc.getElementsByClass("rating_num");
			if(scoreEle != null && !scoreEle.isEmpty())
			{
				String score = scoreEle.first().text();
				if(!StringUtil.isBlank(score))
				{
					float scoref = Float.parseFloat(score);
					movie.setScore(scoref);
				}
				else
				{
					movie.setScore(-1);
				}
			}
			
			//介绍
			Elements des = doc.getElementsByAttributeValueContaining("property", "v:summary");
			if(des != null && !des.isEmpty())
			{
				String description = des.text();
				movie.setDescription(description);
				
			}
		
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return movie;
	}
	
	public Movie analyze(CrawlURI curl)
	{
		Movie movie = null;
		try
		{
			movie = this.analyze(curl.getHost(),curl.getEncode(),curl.getContent());
			Matcher matcher = patternURL.matcher(curl.getUrl());
			if(matcher.find())
			{
				String url = matcher.group();
				movie.setUrl(url);
				movie.setId(Converter.urlEncode(url));
			}
			else
			{
				movie = null;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		return movie;
		
	}
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherHtmlUnit",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
	//	curl.setUrl("http://blog.csdn.net/m13666368773/article/details/8432839");
		curl.setUrl("http://movie.douban.com/subject/6875412/?from=subject-page");
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
		Movie blog = new DoubanMovieAnalyzer().analyze(curl);
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
