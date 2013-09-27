package org.junjun.analyse.analyzerImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.EGCrawlerUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junjun.analyse.analyzer.Analyzer;
import org.junjun.analyse.analyzer.bean.BComment;
import org.junjun.analyse.analyzer.bean.BRelatedMovies;
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.analyse.dao.mongo.MongoDAOapi;

public class MovieCommentAnalyzer1 implements Analyzer<List<BComment>>
{	
	public static String host="movie.douban.com";

	 private Logger logger = Logger.getLogger(MovieCommentAnalyzer1.class);
	 private static final int MovieERROR= 2; 
	 DAOapi dao = null;
	 String idmap="yunfan";
	 static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public MovieCommentAnalyzer1()
	{
		 dao = new MongoDAOapi();
	}
	
	public List<BComment> analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		List<BComment> ret = new ArrayList<BComment>();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);

	//		System.out.println(str);			
			Elements commentsEle = doc.getElementsByClass("comment-item");
			
			for(Element e:commentsEle)
			{
				BComment comment = new BComment();
				
				try
				{
				Element picEle = e.select("img[src]").first();
				String pic = picEle.attr("src");
				comment.setWhoPicUrl(pic);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				
				try
				{
					Element commentInfo= e.select("span[class=comment-info]").first();
					
					try
					{
						String name = commentInfo.select("a[href]").first().text();	
						comment.setWho(name);
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				    
					try
					{
					    String commentContent = e.select("p").first().text();
					    comment.setContent(commentContent);
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
						continue;
					}
					
				    
					try
					{
						 String date = commentInfo.select(":matchesOwn(\\d\\d\\d\\d-\\d)").first().text();
					    comment.setDate(dateFormat.parse(date.trim()));
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					
					}
				    
					try
					{
		   			    
					    String votes = e.select("span[class=comment-vote] > span").first().text();	
					    comment.setVote(Integer.parseInt(votes));
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
						
					}
					
				    
					try
					{
		   			    
						 String clsses = commentInfo.select("span[class*=rating]").first().attr("class"); //allstar10 rating				    
						 String ratingStr =  clsses.replace("allstar", "").replace("rating", "").trim();
						 comment.setRate(Integer.parseInt(ratingStr)/10);
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				   
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			   ret.add(comment);
			}		
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			
			
				return null;
		}
		
		return ret;
	}
	
	public void analyse()
	{		
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		Movie initMovie = new Movie();
		Date date = new Date();
		date.setYear(10);
		int limit = 50;
		initMovie.setCrawledDate(date);	
		List<Movie> movies = dao.getNextMovies(initMovie,limit);
		int count = 0;
		Set<String> updateField = new HashSet<String>();
		updateField.add("magicNum");
	
		// last crawled movie
		Movie pre = initMovie;

		// how many times repeated
		int countRepeat = 0;
		
		while(movies!=null)
		{
			Movie store = null;
			for(Movie movie:movies)
			{				
				logger.info("Movie:"+(count++)+" "+movie.getName()+":"+movie.getUrl());
				System.out.println("Movie:"+(count)+" "+movie.getName()+":"+movie.getUrl());
				String movieurl = movie.getUrl();
				String commentsUrl=null;
				commentsUrl = movie.getUrl().trim()+"/comments";			
				String id = Converter.urlEncode(commentsUrl);
				Html html = dao.getHtml(id);
				
				if(html == null )
					continue;
				
				List<BComment> comments = this.analyze(html);
				if(comments!=null)
					for(BComment c: comments)
					{
						this.dao.insertObj(c);
						logger.info("Comment:"+(count)+" "+c);
						System.out.println("Comment:"+(count)+" "+c);
						
					}
				
				if(movie != null)
				{	
					store = movie;
				}
			}
					
			if(pre.equals(store))
			{
				countRepeat++;
			}
			else
			{
				countRepeat = 0;
			}
			
			if(countRepeat > 3)
				break;

			pre = store;
			movies = dao.getNextMovies(store,limit);
		}
	}
	
	public void test(String url)
	{
		String id = Converter.urlEncode(url);
		Html html  = dao.getHtml(id);
		System.out.println(html);
		List<BComment> comments = this.analyze(html);

		System.out.println(comments);
		
	}
	
	public List<BComment> analyze(Html html)
	{
		  if(html == null)
			return null;
		// TODO Auto-generated method stub
		  List<BComment>  ret = this.analyze(html.getHost(), html.getEncode(), html.getHtml());
		  if(ret!=null)
		  {
			  String url = html.getUrl().trim();
			  String subject = url.replace("/comments", "");
			  for(BComment comment:ret)
			  {
				  comment.setUrl(html.getUrl());			  
				  comment.setComment4(subject);
				  String idStr = comment.getWho()+comment.getContent();
				  comment.setId(Converter.urlEncode(idStr));
			  }
		  }
		  return ret;
	}

	public List<BComment> analyze(Object obj)
	{
		return null;
	}
	static public void main(String [] args) throws ParseException
	{
		MovieCommentAnalyzer1 douban = new MovieCommentAnalyzer1();
		//douban.test("http://movie.douban.com/subject/1291832");
		//douban.test("http://movie.douban.com/subject/23055587");
		//douban.test("http://movie.douban.com/subject/1950253");
		//douban.test("http://movie.douban.com/subject/20425881");
		// test date
		//douban.test("http://movie.douban.com/subject/21354055");
		// test "(" 
		//douban.test("http://movie.douban.com/subject/6120591/comments");
		douban.analyse();
		//Date d = dateFormat.parse("2013-06-30");
		//System.out.println(d);
		
	}



}
