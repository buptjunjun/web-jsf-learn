package org.cb.data;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.apache.log4j.Logger;
import org.cb.data.*;
import org.cb.util.Localizer;
import org.cb.util.converter;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;


/**s
 * Java Spring Data & MongoDB Example
 * 
 */
public class DAOMongo {
	public  final static int INDEXED = 1117;
	public  final static int NOTINDEXED = -1;
	private static final Logger log = Logger.getLogger(DAOMongo.class);
	private MongoOperations mongoOps = null;
	static private DAOMongo mongo = null;
	
	static public DAOMongo getInstance()
	{
		synchronized(DAOMongo.class)
		{
			if(mongo == null)
			{
				 mongo = new DAOMongo(Localizer.getMessage("mongodb"));
			}
		}
		return mongo;
	}
	
	private DAOMongo(String dbName)
	{
		try
		{
			Mongo mongo = new Mongo(Localizer.getMessage("mongohost"));
			mongo.setWriteConcern(WriteConcern.SAFE);
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
	public void updateBlog(List<Blog> lblog)
	{
		if(lblog == null) 
			return;
		for(Blog b: lblog)
		{
			this.updateBlog(b);
		}
	}
	public void updateBlog(Blog blog)
	{
		if(blog == null) 
			return;
		
		mongoOps.updateFirst(new Query(where("_id").is(blog.getId())), new Update().set("magicNum", blog.getMagicNum()), Blog.class);
	}
	
	public List<Blog> searchBlog(String host,int magicNum ,int limit)
	{
    	 HashMap m = new HashMap();
    	 if(host!=null)
    		 m.put("host", host);
    	 m.put("magicNum", magicNum);
    	 
         List<Blog> lp = searchItem(m,limit, Blog.class);
         return lp;
         
	}
	
	public Bloger getBloger(String id)
	{
		if(id == null) return null;
       
		Bloger bloger = this.mongoOps.findById(id, Bloger.class);
        
		return bloger;         
	}
	
	public Blog searchBlog(String id)
	{
    	 HashMap m = new HashMap();
    	 m.put("_id", id);
         List<Blog> lp = searchItem(m,1, Blog.class);
         if(lp!= null && lp.size()>=1)
         {
        	 Blog blog = lp.remove(0);
        	 return blog;
         }
         return null;
         
	}
	
	public List<Blog> searchBlog(String key,List<String> ids)
	{   	
         List<Blog> lp = searchItem(key,ids, ids.size(),Blog.class);
         return lp;
         
	}
//	public List<Blog> searchBlog(String host,int magicNum ,int limit)
//	{
//		 Query q = new Query(Criteria.where("host").is(host).and("magicNum").is(magicNum)).limit(limit);
//         List<Blog> lp = this.mongoOps.find(q, Blog.class);
//         return lp;
//         
//	}
	public <T extends Object>  List<T> searchItem (Map<String ,Object> constrains , int limit, Class cls)
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
	
	public <T extends Object>  List<T> searchItem (String key,List<String> constrains , int limit, Class cls)
	{
		if( constrains == null || constrains.size() == 0|| limit < 1) return null;
		
		Criteria cons = Criteria.where(key).in(constrains);
		Query q = new Query(cons).limit(limit);
		List<T> lp = mongoOps.find(q, cls);
        return lp;     
	}
	
	public Html searchHtml(String id)
	{
    	 HashMap m = new HashMap();
    	 m.put("_id", id);
         List<Html> lp = searchItem(m,1, Html.class);
         if(lp!= null && lp.size()>=1)
         {
        	 Html html = lp.remove(0);
        	 return html;
         }
         return null;
         
	}
	
	public static void main(String[] args)
    {
    //	DAOMongo mongo = new DAOMongo("blogdb");
/*    	List<Blog> lbolg = mongo.searchBlog("blog.csdn.net", -1, 10);
    	for(Blog b:lbolg)
    		System.out.println(b);*/
    	
    //	Blog blog = mongo.searchBlog("ea90e77322c73e4aea1f204ed0eef8e4");
    	
    	DAOMongo.getInstance().testHtml();
       
    }
	
	public void testBlog()
	{
		List<Blog> blogs = mongo.searchBlog("blog.csdn.net", 800, 1);
    	System.out.println("before changing magicNum" + blogs);
    	Blog blog = blogs.get(0); 
    	blog.setMagicNum(1111);
    	mongo.updateBlog(blog);
    	blogs = mongo.searchBlog("blog.csdn.net", 799, 1);
    	System.out.println("after changing the magicNum to 800" + blogs);
	}
	
	public void testHtml()
	{
		String url = "http://blog.chinaunix.net/uid-27018087-id-3465258.html";
		Html html = mongo.searchHtml(converter.urlEncode(url));
		System.out.println(html.host);
    	
	}
}