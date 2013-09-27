package org.junjun.analyse.analyzerImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.EGCrawlerUtil;

import org.junjun.analyse.analyzer.Analyzer;
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.analyse.dao.mongo.MongoDAOapi;

/**
 * reAnalyzer Html and update movie. 
 * @author andyWebsense
 *
 */
public class MovieUpdateAnalyzer implements Analyzer<String>
{	
	public static String host="www.yunfan.com";

	 private Logger logger = Logger.getLogger(MovieUpdateAnalyzer.class);
	 private static final int MovieERROR= 2; 
	 DAOapi dao = null;
	 String idmap="yunfan";
	public MovieUpdateAnalyzer()
	{
		 dao = new MongoDAOapi();
	}
	
	public String analyze(String host,String encode,String content)
	{
		return null;
	}
	
	public void analyse()
	{		
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("name");
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
		
		DoubanMovieAnalyzer movieAnalyzer = new DoubanMovieAnalyzer();
		while(movies!=null)
		{
			Movie store = null;
			for(Movie movie:movies)
			{
				Html html = dao.getHtml(movie.getId());
				logger.info("Movie:"+(count++)+" "+movie.getName()+":"+movie.getUrl());
				System.out.println("Movie:"+(count)+" "+movie.getName()+":"+movie.getUrl());
				
				if(html == null)
					continue;
							
				Movie movieNew = movieAnalyzer.analyze(html);
				if(movieNew!=null)				
				{	
					dao.updateMovie(movieNew, updateFields);
					logger.info("updated Movie :"+(count)+" "+movieNew);
					System.out.println("updated Movie :"+(count)+" "+movieNew);
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
	
	String prefix = "http://www.yunfan.com/s.php?q=";
	public void test(String url)
	{
		String id = Converter.urlEncode(url);
		Movie movie  = dao.getMovie(id);
		System.out.println(movie);
		String name = this.analyze(movie);
		System.out.println("Movie:"+movie.getName()+":"+movie.getUrl()+"--->"+name);
		String query=prefix+name;
		try
		{
			query = prefix+URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(query);
		
	}
	static public void main(String [] args)
	{
		MovieUpdateAnalyzer douban = new MovieUpdateAnalyzer();
		//douban.test("http://movie.douban.com/subject/1291832");
		//douban.test("http://movie.douban.com/subject/23055587");
		//douban.test("http://movie.douban.com/subject/1950253");
		//douban.test("http://movie.douban.com/subject/20425881");
		// test date
		//douban.test("http://movie.douban.com/subject/21354055");
		// test "(" 
		//douban.test("http://movie.douban.com/subject/6425147");
		douban.analyse();
	}

	public String analyze(Html html)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String analyze(Object obj)
	{
		// TODO Auto-generated method stub
		Movie m = (Movie)obj;
		return EGCrawlerUtil.generateQueryOnlyChinese(m.getName());
	}

}
