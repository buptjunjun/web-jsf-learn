package org.easyGoingCrawler.EGCrawler;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyGoingCrawler.DAO.DAOMongo;
import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.analyzer.CSDNBlogAnalyzer;
import org.easyGoingCrawler.bean.BDoubanMovieJson;
import org.easyGoingCrawler.bean.BMovie;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.EGCrawler;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.EGCrawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Query;

public class MovieUpdater
{
	static String host = "movie.douban.com";
	static String baseapi = "https://api.douban.com/v2/movie/";
	// test Crawler
	@Test 
	public void TestCrawler()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
	
		DAOMongo dao = (DAOMongo) appcontext.getBean("DAOMongo");
		Map constrain = new HashMap<String,String>();
		constrain.put("magicNum", -1);
		List<Movie> lmovie = dao.search(constrain,"crawledDate",DAOMongo.DESCENDING, 10,Movie.class);
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		while(true)
		{
			for(Movie m : lmovie)
			{
				
				String id = EGCrawlerUtil.getMovieIDFromUrl(m.getUrl());
				if(id == null)
				{
					System.out.println("#### id == null\n" +m);
				}
				id = id.trim();
				
				String apiurl = baseapi+id;
				CrawlURI curl = new CrawlURI();
				curl.setStatus(-1);
				curl.setUrl(apiurl);
				fetcher.fetch(curl);
				
				if(curl.getHttpstatus() != 200)
				{
					System.out.println("fail:"+curl);
					continue;
				}
				
				BDoubanMovieJson bdm = new BDoubanMovieJson();
				bdm.setJson(curl.getContent());
				bdm.setId(id.trim());
				bdm.setCrawledDate(new Date());
				
				BMovie bm = bdm.toBMovie();			
				this.updateMovie(m, bm,m.getMagicNum()+1);
				
				// insert movie json
				dao.insert(bdm);
				// update movie
				Map map = new HashMap<String,String>();
		    	map.put("id", m.getId());
				dao.update(m, map);
				
				try
				{
					Thread.currentThread().sleep(8);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lmovie = dao.search(constrain,"crawledDate",DAOMongo.ASCENDING, 10,Movie.class);
		}		
	}
	
	public void updateMovie(Movie m, BMovie bm,int magicNum)
	{
		m.setMagicNum(magicNum);
		m.setCrawledDate(new Date());
		
		//设置kind
		String kind = bm.getKind();
		if(kind!=null)
		{
			kind = kind.trim();		
			
			if("movie".equalsIgnoreCase(kind))
			{
				m.setKind("m");
			}
			else
			{
				m.setKind("s");
			}
		}
		
		//更新中文名字
		if(bm.getName()!=null)
			m.setName(bm.getName());
		
		//更新英文名字
		if(bm.getEn_name()!=null)
			m.setEnName(bm.getEn_name());
		
		//更新上映时间
		if(bm.getDate()>1900 )
		{
			Date date = new Date();
			date.setYear(bm.getDate()-1900);
			m.setDate(date);
		}
		
		// 更新 打分人数
		if(bm.getRatings_count()> 0)
		{
			m.setVoteCount(bm.getRatings_count());
		}
		
		// 更新分数
		if(bm.getRating()!=null && bm.getRating().getAverage() >0.0f)
		{
			m.setScore(bm.getRating().getAverage());
		}
		
		//电影时长
		if(bm.getDurations()!=null && bm.getDurations().size() > 0)
		{
			m.setTimespan( bm.getDurations().get(0));
		}
		
		//类型
		if(bm.getType()!=null)
		{
			m.setType(bm.getType());
		}
		
		//国家
		if(bm.getLocation()!=null && bm.getLocation().size() > 0)
			m.setLocation(bm.getLocation().get(0));
		
		//语言
		if(bm.getLanguage()!=null && bm.getLanguage().size() >0)
			m.setLocation(bm.getLanguage().get(0));
		
		if(bm.getDescription()!=null)
			m.setDescription(bm.getDescription());
			
	}
	
	// test blog
	//@Test 
	public void TestBlog()
	{

		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);
		List<Blog> blog = Mongo.mongoOps.find( q_sochina,Blog.class);
		for (Blog b:blog)
		{
			System.out.println(b);
			System.out.println(b.getContent());
			//break;
		}
	
	}
	
	// test bloger
	//@Test 
	public void TestBloger()
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);
		
		List<Bloger> bloger = Mongo.mongoOps.find( q_sochina,Bloger.class);
		for (Bloger b:bloger)
		{
			System.out.println(b);
			//break;
		}
	}
	
}
