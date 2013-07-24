package org.easyGoingCrawler.DAO;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.util.EGCrawlerUtil;
import org.easyGoingCrawler.util.RandomList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;


public class EGDAOMongoMovie implements EGDAO
{
	//MongoTemplate,  test is the db name.
	DAOMongo<Movie> daomongo = null;
    Movie movie = new Movie();
	int magicNum;
	String baseURL = "";
	
    public EGDAOMongoMovie(DAOMongo daomongo, int magicNum ,String baseURL)
	{
    	this.daomongo = daomongo;
    	this.magicNum = magicNum;
    	this.baseURL = baseURL;
	}
    
    public boolean insert(Object obj)
    {
    	try
    	{
    		this.insert(obj);
    		return true;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    }

	public List<CrawlURI> get()
	{
		List<CrawlURI> list = new ArrayList<CrawlURI>();

		List<Movie> tmp  = query(magicNum,10);
		
		if(tmp == null || tmp.size() == 0)
			return list;
		for(Movie m : tmp)
		{
			CrawlURI curl = this.Movie2CrawlURI(m, this.baseURL);

			curl.setReserve(new String(m.getId()));
			list.add(curl);
		}
		RandomList.random(list);
		return list;
	}
    
	
	/**
	 * 
	 * @param magicNum
	 * @param limit
	 * @return
	 */
	public List<Movie> query(int magicNum, int limit)
	{
		List<Movie> list = null;
		try
		{
			HashMap map = new HashMap<String,String>();
			map.put("magicNum", magicNum);
			list = this.daomongo.search(map, "voteCount",DAOMongo.DESCENDING,limit, movie.getClass());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
    
	public boolean update(Movie movie)
	{
		if(movie == null)
			return false;
		
		HashMap map = new HashMap<String,String>();
		map.put("id", movie.getId());
		this.daomongo.update(movie, map);
		
		return true;
	}
	

	
	static public void main(String [] args) throws ParseException
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongoMovie Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongoMovie.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);

		System.out.println();
	}

	

	public List<CrawlURI> get(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateURL(Url url)
	{
		Movie movie = this.daomongo.search(url.getId(), this.movie.getClass());
		
		if(movie == null)
			return false;
		if(url.getFlag() == Url.CRAWLED)
			movie.setMagicNum(this.magicNum+1);
		
		movie.setCrawledDate(new Date());
		HashMap map = new HashMap<String,String>();
		map.put("id", movie.getId());
		this.daomongo.update(movie, map);
		
		return false;
	}
	
	private CrawlURI Movie2CrawlURI(Movie movie,String baseURL)
	{
		CrawlURI curl = new CrawlURI();
		curl.setReserve(movie.getId());
		
		String query = EGCrawlerUtil.generateQueryOnlyChinese(movie);
		System.out.println("movie convert to curl:"+movie+"---"+query);
		try
		{
			query = java.net.URLEncoder.encode(query, "utf-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			query = EGCrawlerUtil.generateQueryOnlyChinese(movie);
		}
		String queryUrl = baseURL.replaceAll("andy2bereplaced", query);
		curl.setUrl(queryUrl);

		return curl;
	}

}
