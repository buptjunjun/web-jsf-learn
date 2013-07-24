package org.easyGoingCrawler.DAO;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.apache.log4j.Logger;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.util.Converter;


import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;


/**s
 * Java Spring Data & MongoDB 
 * implement basic interface of a db: search insert update 
 * 
 */
public class DAOMongo<T> {
	public  final static int ASCENDING = 0;
	public  final static int DESCENDING = 1;
	private static final Logger log = Logger.getLogger(DAOMongo.class);
	MongoOperations mongoOps = null;
	
	public DAOMongo(String dbName)
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
	
	public DAOMongo(String host , int port , String dbName)
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

	/**
	 * update  records which meets the constrains of "constrains"
	 * constrains is a map like : [id:123,"name":"abcd"]
	 * @param obj
	 * @param constrains
	 */
	public void update(T obj,Map<String,String> constrains)
	{
		if(obj == null) 
			return;
		Class cls = obj.getClass();
		Field [] fields = cls.getDeclaredFields();
		Set<String> fieldset = new HashSet<String>();
		for(Field field: fields)
		{
			fieldset.add(field.getName());
		}
		
		this.update(obj,constrains, fieldset);
	}
	
	/**
	 * update  records which meets the constrains of "constrains"
	 * constrains is a map like : [id:123,"name":"abcd"]
	 * @param obj
	 * @param constrains
	 */
	public void update(T obj,Map<String,String> constrains,Set<String> fieldNames)
	{
		if(obj == null) 
			return;
		
		Criteria cons = null;
		boolean flag = false;
		for(Map.Entry entry:constrains.entrySet())
		{
			
			String key = (String)entry.getKey();
			if(flag == false)
				cons = Criteria.where(key).is(entry.getValue());
			else
				cons = cons.and(key).is(entry.getValue());
			flag = true;
		}
		
		if(flag == false)
			return ;
		Query q = new Query(cons);
		Update update = new Update();
		
		Class cls = obj.getClass();
		Field [] fields = cls.getDeclaredFields();
		for(Field field : fields)
		{
			String fieldName = field.getName();
			if(!fieldNames.contains(fieldName))
				continue;
			try
			{
				field.setAccessible(true);
				update.set(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mongoOps.updateFirst(q, update, cls );
	}
	
	/**
	 * search a record according to the id
	 * @param id
	 * @param cls
	 * @return
	 */
	public T search(String id,Class cls)
	{
    	 HashMap m = new HashMap();
    	 m.put("id", id);
         List<T> lp = search(m,1,cls);
         if(lp!= null && lp.size()>=1)
         {
        	 T obj = (T) lp.remove(0);
        	 return obj;
         }
         return null;
         
	}
	
	/**
	 * search records which meets the constrains of "constrains"
	 * constrains is a map like : [id:123,"name":"abcd"]
	 * @param <T>
	 * @param constrains
	 * @param limit
	 * @param cls
	 * @return
	 */
	public <T extends Object>  List<T> search (Map<String ,Object> constrains ,  int limit, Class cls)
	{
		if( constrains == null || limit < 1) return null;
		Criteria cons = null;
		boolean flag = false;
		for(Map.Entry entry:constrains.entrySet())
		{
			
			String key = (String)entry.getKey();
			if(flag == false)
				cons = Criteria.where(key).is(entry.getValue());
			else
				cons = cons.and(key).is(entry.getValue());
			flag = true;
		}
		
		if(flag == false)
			return null;
		
		Query q = new Query(cons).limit(limit);
		List<T> lp = mongoOps.find(q, cls);
        return lp;
         
	}
	
	/**
	 * search records which meets the constrains of "constrains"
	 * constrains is a map like : [id:123,"name":"abcd"]
	 * @param <T>
	 * @param constrains
	 * @param limit
	 * @param cls
	 * @return
	 */
	public <T extends Object>  List<T> search (Map<String ,Object> constrains , String sortFiled,int sortWay, int limit, Class cls)
	{
		if( constrains == null || limit < 1) return null;
		Criteria cons = null;
		boolean flag = false;
		for(Map.Entry entry:constrains.entrySet())
		{
			
			String key = (String)entry.getKey();
			if(flag == false)
				cons = Criteria.where(key).is(entry.getValue());
			else
				cons = cons.and(key).is(entry.getValue());
			flag = true;
		}
		
		if(flag == false)
			return null;
		
		Query q = new Query(cons).limit(limit);
		if(sortWay == ASCENDING)
			q.sort().on(sortFiled, Order.ASCENDING);
		else if (sortWay == DESCENDING)
			q.sort().on(sortFiled, Order.DESCENDING);
		
		List<T> lp = mongoOps.find(q, cls);
        return lp;
         
	}
	
	/**
	 * update  records which meets the constrains of "constrains"
	 * the constrains is key in the set of "constrains";
	 * @param <T>
	 * @param key
	 * @param constrains
	 * @param limit
	 * @param cls
	 * @return
	 */
	public <T extends Object>  List<T> search (String key,List<String> constrains , int limit, Class cls)
	{
		if( constrains == null || constrains.size() == 0|| limit < 1) return null;
		Criteria cons = null;
		boolean flag = false;
		cons = Criteria.where(key).in(constrains);
		
		Query q = new Query(cons).limit(limit);
		List<T> lp = mongoOps.find(q, cls);
        return lp;     
	}
	
	
/**
 * insert a record
 * @param obj
 * @return
 */
  public boolean insert(T obj)
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
	  
	public static void main(String[] args)
    {
    	DAOMongo<Movie> mongo = new DAOMongo<Movie>("moviedb");
/*    	List<Blog> lbolg = mongo.searchBlog("blog.csdn.net", -1, 10);
    	for(Blog b:lbolg)
    		System.out.println(b);*/
    	
    //	Blog blog = mongo.searchBlog("ea90e77322c73e4aea1f204ed0eef8e4");
    	Movie movie = new Movie();
    	movie.setUrl("aaaaa");
    	movie.setName("test1");
    	movie.setId(Converter.urlEncode(movie.getUrl()));
   	
    	mongo.insert(movie);
    	
    	Movie result = mongo.search(movie.getId(), movie.getClass());
    	System.out.println(movie);
   	
    	movie.setName("test2");
    	String [] l =  {"hhh","bbb"};
    	List list = Arrays.asList(l);
    	movie.setActors(list);
       
    	Map m = new HashMap<String,String>();
    	m.put("id", movie.getId());
    	mongo.update(movie, m);
    	
    	 result = mongo.search(movie.getId(), movie.getClass());
    	System.out.println(movie);
    	
    }
}