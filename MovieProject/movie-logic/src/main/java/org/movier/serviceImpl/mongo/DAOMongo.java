package org.movier.serviceImpl.mongo;

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

import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;


/**
 * Java Spring Data & MongoDB 
 * implement basic interface of a db: search insert update 
 * 
 */
public class DAOMongo<T> {
	public  final static int ASCENDING = 0;
	public  final static int DESCENDING = 1;
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
	
	public DAOMongo(String host , int port , String dbName,String name,String password)
	{
		try
		{
			Mongo mongo = new Mongo(host,port);
			mongo.setWriteConcern(WriteConcern.NONE);
			UserCredentials userCredentials = new UserCredentials(name, password);
			MongoDbFactory factory = new  SimpleMongoDbFactory(mongo,dbName, userCredentials);
			mongoOps = new MongoTemplate(factory);
			
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
	public void update(T obj,Map<String,Object> constrains)
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
		
		this.update(obj, null, null, constrains, fieldset);
	}
	
	/**
	 * update  records which meets the constrains of "constrains"
	 *  constrains is a map like : {"value":100} indicates value =100  
	 * constrainsGT is constrain greater than, for example, {"value":100} indicates value >=100  
	 *  constrainsGT is constrain less than,or example, {"value":100} indicates value < 100
	 *    
	 * @param obj
	 * @param constrains
	 *  @param constrainsLT
	 *   @param constrainsGT
	 * @param fieldNames which fields you want to update
	 */
	public void update(T obj,Map<String ,Object> constrainsLT,Map<String ,Object> constrainsGT,Map<String, Object> constrains ,Set<String> fieldNames)
	{
		if(obj == null) 
			return;
		
		Criteria cons = this.getCriteria(constrainsLT, constrainsGT, constrains);
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
	 * search records which meets the constrains of "constrains"
	 * constrains is a map like : {"value":100} indicates value =100  
	 * constrainsGT is constrain greater than, for example, {"value":100} indicates value >=100  
	 *  constrainsGT is constrain less than,or example, {"value":100} indicates value < 100  
	 * @param <T>
	 * @param constrains
	 * @param constrainsGT
	 * 	@param constrainsLT
	 * @param limit
	 * @param cls
	 * @return
	 */
	public <T extends Object>  List<T> search (Map<String ,Object> constrainsLT,Map<String ,Object> constrainsGT,Map<String ,Object> constrains , String sortFiled,int sortWay, int limit, Class cls)
	{	
		Criteria cons =  getCriteria(constrainsLT,constrainsGT,constrains);
		Query q = new Query(cons).limit(limit);
		if(sortWay == ASCENDING)
			q.sort().on(sortFiled, Order.ASCENDING);
		else if (sortWay == DESCENDING)
			q.sort().on(sortFiled, Order.DESCENDING);
		
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
	  
  /**
	 * search records which meets the constrains of "constrains"
	 * constrains is a map like : {"value":100} indicates value =100  
	 * constrainsGT is constrain greater than, for example, {"value":100} indicates value >=100  
	 *  constrainsGT is constrain less than,or example, {"value":100} indicates value < 100  
	 * @param constrains
	 * @param constrainsGT
	 * 	@param constrainsLT
	 * @param cls
	 * @return
	 */
 private Criteria getCriteria (Map<String ,Object> constrainsLT, Map<String ,Object> constrainsGT,Map<String ,Object> constrains)
 {
	
	Criteria cons = null;
	if(constrains !=null)
	{
		for(Map.Entry entry:constrains.entrySet())
		{
			
			String key = (String)entry.getKey();
			if(cons == null)
				cons = Criteria.where(key).is(entry.getValue());
			else
				cons = cons.and(key).is(entry.getValue());
			
		}
	}
	
	// gt
	if(constrainsGT !=null)
	{
		for(Map.Entry entry:constrainsGT.entrySet())
		{
			
			String key = (String)entry.getKey();
			if(cons == null)
				cons = Criteria.where(key).gte(entry.getValue());
			else
				cons = cons.and(key).gte(entry.getValue());
		
		}
	}
	
	// LT
	if(constrainsLT !=null)
	{
		for(Map.Entry entry:constrainsLT.entrySet())
		{
			
			String key = (String)entry.getKey();
			if(cons == null)
				cons = Criteria.where(key).lte(entry.getValue());
			else
				cons = cons.and(key).lte(entry.getValue());
		}
		
		if(cons == null)
			return null;
	}
	
	return cons;
}
 

}