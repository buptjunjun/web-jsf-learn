package org.easyGoingCrawler.DAO;

import java.net.UnknownHostException;
import java.util.List;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class EGDAOMongo
{
	private  Mongo mongoServer= null;
	
	//MongoTemplate,  test is the db name.
    private MongoOperations mongoOps = null;
    
    public EGDAOMongo(Mongo mongoServer,MongoOperations mongoOps)
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
    
    
}
