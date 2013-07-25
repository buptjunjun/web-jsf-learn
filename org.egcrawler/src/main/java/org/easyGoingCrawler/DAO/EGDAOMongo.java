package org.easyGoingCrawler.DAO;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
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


public class EGDAOMongo implements EGDAO
{
	private  Mongo mongoServer= null;
	//MongoTemplate,  test is the db name.
    public MongoOperations mongoOps = null;    
    
	private List<String> hosts = null;
    
	@Autowired()
	@Qualifier("pattern_save")
    private Map<String,String> pattern_save = new HashMap<String,String>();
    
	
	
    public EGDAOMongo(Mongo mongoServer,String dbName, List<String> hosts )
	{
    	this.mongoServer = mongoServer;
    	mongoServer.setWriteConcern(WriteConcern.NONE);
    	this.mongoOps = new org.springframework.data.mongodb.core.MongoTemplate(this.mongoServer,dbName);
    	this.hosts = hosts;

	}
    
    public boolean insert(Object obj)
    {
    	try
    	{
    		this.mongoOps.insert(obj);
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
		for(String h : hosts)
		{
			List tmp  = queryByhost(h,10);
			if(tmp == null || tmp.size() == 0)
				continue;
			list.addAll(tmp);
		}
		RandomList.random(list);
		return list;
	}
    
	
	public List<CrawlURI> queryByhost(String host, int limit)
	{
		List<CrawlURI> list = new ArrayList<CrawlURI>();
	
		try
		{
			Query q ;
			
			// querying a blog's probability is  70% 
			if( new Random().nextInt(10) < 3)
				q= new Query(Criteria.where("flag").is(Url.UNCRAWLED).and("host").is(host));
			else
				q = new Query(Criteria.where("flag").is(Url.UNCRAWLED).and("host").is(host).and("type").is(Url.URL_BLOG));
		
			//q = new Query(Criteria.where("flag").is(0).and("url").regex("(http://home.cnblogs.com/u/[a-zA-z|0-9|_|-]+[/]?)|(http://home.cnblogs.com/u/[a-zA-z|0-9|_|-]+/followers[/]?.*)|(http://home.cnblogs.com/u/[a-zA-z|0-9|_|-]+/followees[/]?.*)"));
	        q.sort().on("date", Order.ASCENDING);
	        q.limit(limit);
	             
	        List<Url> lp = mongoOps.find(q, Url.class);
	        
	        
	        if ( lp != null)
	        {
	        	for(Url url : lp )
	        	{
	        		list.add(url.toCrawlURI());
	        	}
	        }
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
    
	public boolean updateURL(Url uri)
	{
		if(uri == null)
			return false;
		
		mongoOps.upsert(new Query(where("id").is(uri.getId())), new Update().set("flag", Url.CRAWLED).set("lastCrawled", uri.getLastCrawled()), Url.class);
		return true;
	}
	
	public void setPattern_save(Map<String, String> pattern_save)
	{
		this.pattern_save = pattern_save;
	}

	public List<CrawlURI> get(String key)
	{
		// TODO Auto-generated method stub
		return this.queryByhost(key, 10);
	}

	
	/**
	 * 去的最近的html
	 * @return
	 */
	public List<Html> getLatestHtml(String host,int limit)
	{
		List<Html> html = new ArrayList<Html>();
		Query q = new Query(where("host").is(host));
		q.limit(limit);
		q.sort().on("crawledDate", Order.DESCENDING);
		html = this.mongoOps.find(q, Html.class);
		return html;
	}
	
	/**
	 * 去的最近的html
	 * @return
	 */
	public List<Url> getLatestUrl(String host,int limit)
	{
		List<Url> html = new ArrayList<Url>();
		Query q = new Query(where("host").is(host));
		q.limit(limit);
		q.sort().on("lastCrawled", Order.DESCENDING);
		html = this.mongoOps.find(q, Url.class);
		return html;
	}
	
	/**
	 * 去的最近的html
	 * @return
	 */
	public List<Blog> getLatestBlog(String host,int limit)
	{
		List<Blog> blog = new ArrayList<Blog>();
		Query q = new Query(where("host").is(host));
		q.limit(limit);
		q.sort().on("crawledDate", Order.DESCENDING);
		blog = this.mongoOps.find(q, Blog.class);
		return blog;
	}
	
	/*
	 * get the size of a collection
	 */
	public long getCollectionCount(Class cls)
	{
		Query q = new Query();
		return this.mongoOps.count(q, cls);
	
	}
	
	/*
	 * get the size of a collection
	 */
	public long getCollectionCountQuery(Query q,Class cls)
	{
	
		return this.mongoOps.count(q,cls );
		
	}
	
	/*
	 * get the size of a collection
	 */
	public long getCollectionCountByTime(String field,Date start , Date end,Class cls)
	{
		Query q = new Query(where(field).gt(start).lte(end));
		return this.mongoOps.count(q,cls);
		
	}
	
	/*
	 * get the size of a collection
	 */
	public long getCollectionCountByHost(String host,Class cls)
	{
		Query q = new Query(where("host").is(host));
		return this.mongoOps.count(q,cls);
		
	}
	
	/*
	 * get the size of a collection
	 */
	public long getCollectionCountByTimeByHost(String host,String field,Date start , Date end,Class cls)
	{
		Query q = new Query(where("host").is(host).and(field).gt(start).lte(end));
		return this.mongoOps.count(q,cls);
		
	}
	
	public List getItemByRegex(String field,String regex,int limit,Class cls )
	{
		Query q = new Query(where(field).regex(regex));
		q.limit(limit);
		return this.mongoOps.find(q, cls);
	}
	
	
	static public void main(String [] args) throws ParseException
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongo Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
		Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);
		
		
//		//Blog blog = Mongo.mongoOps.findOne( q_chinaunix,Blog.class);
//		List<Blog> blog = Mongo.mongoOps.find( q_51cto,Blog.class);
//		for (Blog b:blog)
//		{
//			System.out.println(b);
//			try
//			{
//				Document doc = Jsoup.parse(b.getContent());
//				String content = doc.text();
//				System.out.println(content+"\n\n");
//			} catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//break;
//		}
//		java.text.SimpleDateFormat f = new  java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
//		Date start = f.parse("2013-2-1 00:00");
//		Date end = f.parse("2013-3-1 00:00");
//		long amount = Mongo.getCollectionCount(Blog.class);
//		long amount1 = Mongo.getCollectionCountByTime("crawledDate",start,end , Blog.class);
		List<Url> ret = Mongo.getItemByRegex("url","http://home.cnblogs.com/u/[a-zA-z|0-9|_|-]+/",1000,Url.class);
		long count = Mongo.getCollectionCount(Blog.class);
		System.out.println();
	}

}
