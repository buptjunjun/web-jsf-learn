package org.easyGoingCrawler.DAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
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

import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class EGDAOMongo implements EGDAO
{
	private  Mongo mongoServer= null;
	//MongoTemplate,  test is the db name.
    private MongoOperations mongoOps = null;    
    
	private List<String> hosts = null;
    
	@Autowired()
	@Qualifier("pattern_save")
    private Map<String,String> pattern_save = new HashMap<String,String>();
    
    public EGDAOMongo(Mongo mongoServer,String dbName, List<String> hosts )
	{
    	this.mongoServer = mongoServer;
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
		return list;
	}
    
	
	public List<CrawlURI> queryByhost(String host, int limit)
	{
		List<CrawlURI> list = new ArrayList<CrawlURI>();
	
		try
		{
			String pattern4save = this.pattern_save.get(host);
			Query q ;
			
//			// querying a blog's probability is  70% 
//			if( new Random().nextInt(10) < 3)
//				q= new Query(Criteria.where("flag").is(Url.UNCRAWLED).and("host").is(host));
//			else
				q = new Query(Criteria.where("flag").is(Url.UNCRAWLED).and("host").is(host).and("url").regex(pattern4save));
			
	        q.sort().on("date", Order.ASCENDING);
	        q.limit(10);
	             
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

	
}
