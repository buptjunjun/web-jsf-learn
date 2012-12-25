package org.easyGoingCrawler.DAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
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
    
    private List<String> host = null;
    
    public EGDAOMongo(Mongo mongoServer,MongoOperations mongoOps )
	{
    	this.mongoServer = mongoServer;
    	this.mongoOps = mongoOps;
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
		for(String h : host)
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
			Query q = new Query(Criteria.where("flag").is(Url.UNCRAWLED).and("host").is(host));
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
		mongoOps.upsert(new Query(where("id").is(uri.getId())), new Update().set("flag", Url.CRAWLED), Url.class);
		return true;
	}
	
	public Mongo getMongoServer()
	{
		return mongoServer;
	}

	public void setMongoServer(Mongo mongoServer)
	{
		this.mongoServer = mongoServer;
	}

	public MongoOperations getMongoOps()
	{
		return mongoOps;
	}

	public void setMongoOps(MongoOperations mongoOps)
	{
		this.mongoOps = mongoOps;
	}

	public List<String> getHost()
	{
		return host;
	}

	public void setHost(List<String> host)
	{
		this.host = host;
	}

}
