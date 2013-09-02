package org.junjun.analyse.analyzerImpl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junjun.analyse.analyzer.Analyzer;
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.analyse.dao.mongo.MongoDAOapi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DoubanMovieAnalyzer implements Analyzer<Movie>
{	
	public static String host="movie.douban.com";
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private Pattern patternInt = Pattern.compile("\\d+");
	private Pattern patternURL = Pattern.compile("http://movie.douban.com/subject/\\d+");
	private Pattern patternDate = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
	 static String loc = "制片国家/地区: ";
	 static String language = "语言:";
	 static String date = "日期:"; 
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
				if(e!=null)
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
				int i= 0;
				do
				{
					typeList.add(e.text());
					e = e.nextElementSibling();
					if(e!=null && e.tagName().equals("br") || i++ > 3) break;
				}
				while(e!=null);
				movie.setType(typeList);
			}

			//评分人数
			Elements votes = doc.getElementsByAttributeValue("property", "v:votes");
			if(votes!=null && !votes.isEmpty())
			{
				Element vote = votes.first();
				if(vote!=null)
				{
					String countStr = vote.text();
					try
					{
						int countInt = Integer.parseInt(countStr);
						movie.setVoteCount(countInt);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
			}
			
			// 国家
			int start = str.indexOf(loc);
			int end = str.indexOf(language);
			if(start>0)
			{
				String location = str.substring(start+lengthLoc, end>0?end:start+lengthLoc+5);
				movie.setLocation(location);
			}
			// 日期
			Matcher mdate = patternDate.matcher(str);
			if(mdate.find())
			{
				String date = mdate.group();
				Date d = formater.parse(date);
				movie.setDate(d);
			}
			
			// 片长
			start = str.indexOf(length);
			end = str.indexOf(aname);
			if(start>0)
			{
				String length = str.substring(start+lengthLength,end<0?start+lengthLength+10:end);		
				Matcher m = patternInt.matcher(length);
				if(m.find())
				{	length = m.group();
					int len = Integer.parseInt(length);
					movie.setTimespan(len);
				}
			}
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
					try
					{
						float scoref = Float.parseFloat(score);
						movie.setScore(scoref);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					movie.setScore(0);
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
	

	public Movie analyze(Html html)
	{
		Movie movie = this.analyze(html.getHost(), html.getEncode(), html.getHtml());
		movie.setUrl(html.getUrl());
		movie.setId(Converter.urlEncode(movie.getUrl()));
		return movie;
	}
	
	public void analyse()
	{
		DAOapi dao = new MongoDAOapi();
		
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		
		Html html = dao.getNextHtml(host);
		while(html!=null)
		{
			Movie movie = this.analyze(html);
			
			dao.insertMovie(movie);
			dao.updateHtml(html, updateFields);
			
			html = dao.getNextHtml(host);
		}
	}
	static public void main(String [] args)
	{
		new DoubanMovieAnalyzer().analyse();
	}

}
