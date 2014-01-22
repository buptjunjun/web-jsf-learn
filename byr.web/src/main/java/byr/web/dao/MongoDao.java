package byr.web.dao;


import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;


import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import org.apache.log4j.Logger;
import byr.analyzer.analyzer.HtmlStructuredData;

/**s
 * Java Spring Data & MongoDB 
 * implement basic interface of a db: search insert update 
 * 
 */
public class MongoDao 
{
	public  final static int LIMIT = 50;
	public  final static int ASCENDING = 0;
	public  final static int DESCENDING = 1;
	
	private static final Logger log = Logger.getLogger(MongoDao.class);
	
	private MongoOperations mongoOps = null;
	public MongoDao(String dbName)
	{
		try
		{
			Mongo mongo = new Mongo("");
			mongo.setWriteConcern(WriteConcern.NONE);
			mongoOps = new MongoTemplate(mongo, dbName);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MongoDao(String host , int port , String dbName)
	{
		try
		{
			Mongo mongo = new Mongo(host,port);
			mongo.setWriteConcern(WriteConcern.NONE);
			mongoOps = new MongoTemplate(mongo, dbName);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<HtmlStructuredData> search(String keyword, int sort, int limit, Date date1,Date date2)
	{
		if (limit <= 0)
			limit = LIMIT;
		
		List<HtmlStructuredData> ret = null;		
		keyword = keyword.trim().replaceAll("[\\s+]", ".*");
		String pattern = ".*"+keyword+".*";
		Criteria cons = Criteria.where("title").regex(pattern);			
		cons.orOperator(Criteria.where("content").regex(pattern));
		
		if ( date1 != null)
			cons.and("createdDate").gte(date1);
		if( date2 != null)
			cons.and("createdDate").lte(date2);
		Query q = new Query(cons).limit(limit);
		
		if(sort == MongoDao.ASCENDING)
			q.sort().on("createdDate", Order.ASCENDING);
		else if(sort == MongoDao.DESCENDING)
			q.sort().on("createdDate", Order.DESCENDING);
			
		ret  = mongoOps.find(q,HtmlStructuredData.class);
		
		return ret;
	}
	
	
	public static void main(String [] args)
	{
		new MongoDao("byr").search("м╪пн  ", 1, 2, null, null);
	}
}