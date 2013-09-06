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
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.analyse.dao.mongo.MongoDAOapi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DoubanMovieAnalyzer implements Analyzer<Movie>
{	
	public static String host="movie.douban.com";
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM");
	private SimpleDateFormat formater2 = new SimpleDateFormat("yyyy");
	private Pattern patternInt = Pattern.compile("\\d+");
	private Pattern patternURL = Pattern.compile("http://movie.douban.com/subject/\\d+");
	private Pattern patternDate = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
	private Pattern patternDate1 = Pattern.compile("\\d\\d\\d\\d-\\d\\d");
	private Pattern patternDate2 = Pattern.compile("\\d\\d\\d\\d");
	
	 static String director = "导演";
	 static String actor = "主演";
	 static String type = "类型"; 
	 static String loc = "制片国家/地区: ";
	 static String language = "语言:";
	 static String date = "日期:"; 
	 static String length = "片长:";
	 static String aname = "又名:";
	 static String imdb = "IMDb";
	 static String seperator =  "/";
	
	 static int lengthLoc = loc.length();
	 static int lengthDate = date.length(); 
	 static int lengthLength = length.length();
	 static int lengthAname = aname.length();
	 private Logger logger = Logger.getLogger(DoubanMovieAnalyzer.class);
	 private static final int HTMLERROR= 2; 
	 DAOapi dao = null;
	public DoubanMovieAnalyzer()
	{
		 dao = new MongoDAOapi();
	}
	
	public Movie analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Movie movie = new Movie();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);
					
	//		System.out.println(str);
			//片名
			String name = doc.title();
			name = name.replace("(豆瓣)", "");
			movie.setName(name);		
			
			Element infoElem = doc.getElementById("info");		
			List<Node> infoNodes = infoElem.childNodes();	

			// to save the infomation of a movie 
			Map<String,String> infos = new HashMap<String,String>();
			
			
			// get the pieces of  information
			String text = "";
			for(Node node:infoNodes)
			{
				
				if(node.toString().contains("<br />")||node.toString().contains("<br>")||node.toString().contains("<br/>"))
				{
					
					Document d = Jsoup.parse(text);
					text = d.text();
					if(StringUtils.isBlank(text)) 
						continue;
					
					String [] splits = text.split(":");
					if(splits!=null && splits.length >=2)
					infos.put(splits[0].trim(), splits[1].trim());
					text = "";
				}
				else
				{
					text+=node.toString();
				}	
			}
			
			
			for(Entry<String,String> entry:infos.entrySet())
			{
				String key =  entry.getKey();
				String value = entry.getValue();
				
				try
				{
					if(key.contains("导演"))
					{
						String []arrs = value.split(seperator);
						movie.setDirector(Arrays.asList(arrs));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}

				try
				{
					if(key.contains("主演"))
					{
						String []arrs = value.split(seperator);
						movie.setActors(Arrays.asList(arrs));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("又名"))
					{
						String []arrs = value.split(seperator);
						movie.setAnotherName(Arrays.asList(arrs));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("类型"))
					{
						String []arrs = value.split(seperator);
						movie.setType(Arrays.asList(arrs));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("国家") || key.contains("地区"))
					{
						String []arrs = value.split(seperator);
						movie.setLocation(Arrays.asList(arrs));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("语言"))
					{
						String []  arrs = value.split(seperator);
						movie.setLocation(Arrays.asList(arrs));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("日期")|| key.contains("首播"))
					{
						Matcher mdate = patternDate.matcher(value);
						if(mdate.find())
						{
							String date = mdate.group();
							Date d = formater.parse(date);
							movie.setDate(d);
						}
						
						mdate = patternDate1.matcher(value);
						if(mdate.find())
						{
							String date = mdate.group();
							Date d = formater1.parse(date);
							movie.setDate(d);
						}
						
						mdate = patternDate2.matcher(value);
						if(mdate.find())
						{
							String date = mdate.group();
							Date d = formater2.parse(date);
							movie.setDate(d);
						}
					}
					
					if(movie.getDate() == null)
					{
						Element edate = doc.getElementsByClass("year").first();
						String dateStr = edate.text();
						Matcher mdate = patternDate2.matcher(dateStr);
						if(mdate.find())
						{
							String date = mdate.group();
							Date d = formater2.parse(date);
							movie.setDate(d);
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("片长"))
					{					
						Matcher m = patternInt.matcher(value);
						if(m.find())
						{	length = m.group();
							int len = Integer.parseInt(length);
							movie.setTimespan(len);
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("季数"))
					{					
						Matcher m = patternInt.matcher(value);
						if(m.find())
						{	
							String session = m.group();
							int len = Integer.parseInt(session);
							movie.setSeason(len%10);
							movie.setKind("s");
						}
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
				try
				{
					if(key.contains("集数"))
					{					
						Matcher m = patternInt.matcher(value);
						if(m.find())
						{	String episode = m.group();
							int len = Integer.parseInt(episode);
							movie.setEpisode(len);
							movie.setKind("s");
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e.toString());
				}
				
			}
		
			try
			{
				//介绍
				Elements des = doc.getElementsByAttributeValueContaining("property", "v:summary");
				if(des != null && !des.isEmpty())
				{
					String description = des.text();
					movie.setDescription(description);
					
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error(e.toString());
			}
			
			try
			{
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
							logger.error(e.toString());
						}
						
					}
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error(e.toString());
			}
			
			try
			{
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
							logger.error(e.toString());
						}
					}
					else
					{
						movie.setScore(0);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error(e.toString());
			}

		
			try
			{
				//pictures
				Element imgEle = doc.getElementById("mainpic");
				String posterUrl = "";
				if(imgEle != null )
				{
					try
					{
						Element imgsEle = imgEle.getElementsByTag("img").first();
						posterUrl = imgsEle.attr("src");
					}
					catch(Exception e){e.printStackTrace();logger.error(e.toString());}
				}
				
				System.out.println();
				//related pictures
				Element relatedPicEle = doc.getElementsByClass("related-pic").first();
				List<String> pictures = new ArrayList<String>();
				if(relatedPicEle != null )
				{
					try
					{
						Elements imgsEles = relatedPicEle.getElementsByTag("img");
						Iterator i = imgsEles.iterator();
						while(i.hasNext())
						{
							Element imgsEle = (Element) i.next();
							String url = imgsEle.attr("src");
							if(!StringUtils.isBlank(url))
							{
								pictures.add(url.trim());
							}
						}
							
					}
					catch(Exception e){e.printStackTrace();}
				}
				
				if(!StringUtils.isBlank(posterUrl))
				{
					pictures.add(0, posterUrl);
				}
				
				movie.setPictures(pictures);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error(e.toString());
			}
			
			
			
			try
			{
				//tags
				Element tagEle = doc.getElementsByClass("tags-body").first();
				List<String> tags = new ArrayList<String>();
				if(tagEle != null )
				{
					try
					{
						Elements tagEles = tagEle.getElementsByTag("a");
						Iterator<Element> i = tagEles.iterator();
						while(i.hasNext())
						{
							Element imgsEle =i.next();
							String tagtext = imgsEle.text();
							if(!StringUtils.isBlank(tagtext) )
							{								
								String [] splits = tagtext.split("\\(");
								if(splits.length>1)
									tags.add(splits[0].trim());
							}
							
						}
						movie.setTags(tags);						
					}
					catch(Exception e){e.printStackTrace();logger.error(e.toString());}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error(e.toString());
			}

			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			
			if(!StringUtils.isBlank(movie.getName()))
				return movie;
			else
				return null;
		}
		
		return movie;
	}
	

	public Movie analyze(Html html)
	{
		Movie movie = this.analyze(html.getHost(), html.getEncode(), html.getHtml());
		if(movie==null)
			return null;
		
		movie.setUrl(html.getUrl());
		movie.setId(Converter.urlEncode(movie.getUrl()));
		return movie;
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
				Movie movie = this.analyze(html);	
				
				if(movie!=null)
				{
					dao.insertMovie(movie);
					logger.info("movie:"+(count)+movie);
					System.out.println("movie:"+(count)+movie);
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
		Movie m = this.analyze(html);
		System.out.println(m);
	}
	static public void main(String [] args)
	{
		DoubanMovieAnalyzer douban = new DoubanMovieAnalyzer();
		//douban.test("http://movie.douban.com/subject/1291832");
		//douban.test("http://movie.douban.com/subject/23055587");
		//douban.test("http://movie.douban.com/subject/1950253");
		//douban.test("http://movie.douban.com/subject/20425881");
		// test date
		//douban.test("http://movie.douban.com/subject/21354055");
		// test analyzer fail
		douban.test("http://movie.douban.com/subject/1906978");
		//douban.analyse();
	}

}
