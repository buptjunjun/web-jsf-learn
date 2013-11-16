package org.junjun.controller.logic;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.bean.part1.UIComment;
import org.junjun.bean.part1.User;
import org.junjun.mongo.DAOMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

public class PicServicesMongo implements PicServices{

	public static final  String dbname = "picdb";
	MongoOperations mongoOps = null;
	public static String host = "199.38.181.47";
	public static int port = 27017;
	public static String name = "picfalls";
	public static String password="1234abcd1";
	
	public PicServicesMongo() {
		try
		{
			Mongo mongo = new Mongo(host,port);
			mongo.setWriteConcern(WriteConcern.NONE);
			UserCredentials userCredentials = new UserCredentials(name, password);
			MongoDbFactory factory = new  SimpleMongoDbFactory(mongo,dbname, userCredentials);
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
		
	/*	try
		{
			Mongo mongo = new Mongo(host,port);
			mongo.setWriteConcern(WriteConcern.NONE);
			mongoOps = new MongoTemplate(mongo, dbname);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	  
	
	public List<Tag> getTag() {
		// TODO Auto-generated method stub
		return mongoOps.findAll(Tag.class);
	}
	
	
	public User getUser(String id) 
	{
		return mongoOps.findById(id, User.class);
	}
	
	
	public void updateUser(User user) {
		if(user == null)
			return ;
		Class cls = user.getClass();
		Query q = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		
		Field [] fields = cls.getDeclaredFields();
		for(Field field : fields)
		{
			String fieldName = field.getName();
			try
			{
				field.setAccessible(true);
				update.set(field.getName(), field.get(user));
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
	
	
	public List<UIComment> getUIComments(String id) {
		// TODO Auto-generated method stub		
		List<UIComment> uicomments = new ArrayList<UIComment>();
		if(id !=null)
		{
			Query query = new Query(Criteria.where("commentTo").is(id));
			List<Comment> comments = mongoOps.find(query, Comment.class);
			
			if(comments!=null)
			{
				for(Comment comment : comments)
				{
					UIComment uicomment = new UIComment();						
					User user = this.getUser(comment.getCommentFrom());
					uicomment.setComment(comment);
					if(user !=null)
					{
						uicomment.setUser(user);	
						user.setId(null);
						user.setOtherInfo(null);
						user.setPassword(null);
						user.setIdSource(null);
						user.setIdSource(null);
					}
					uicomments.add(uicomment);
				}
				
				return uicomments;
			}
		}
		
		return null;
	}
	


	
	
	public void insertComment(Comment comment) {
		// TODO Auto-generated method stub
		this.mongoOps.insert(comment);
		Item item = this.getItem(comment.getCommentTo());
		item.setComment(item.getComment()+1);		
		this.updateItem(item);
	}
	
	
	public void insertItem(Item item) {
		this.mongoOps.insert(item);
	}
	
	
	public Item getItem(String id) {
		
		return this.mongoOps.findById(id, Item.class); 
		
	}
	public void updateItem(Item item) 
	{
		if(item == null)
			return;
		
		Class cls = item.getClass();
		Query q = new Query(Criteria.where("id").is(item.getId()));
		Update update = new Update();
		
		Field [] fields = cls.getDeclaredFields();
		for(Field field : fields)
		{
			String fieldName = field.getName();
			try
			{
				field.setAccessible(true);
				update.set(field.getName(), field.get(item));
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
	
	
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		this.mongoOps.insert(obj);
	}
	


	

	
	public List<Item> getItemByTag(String tag,int page,String sort,int limit)
	{
		// exit the max SKIP ;
		if(page > Constant.MAX_SKIP)
			return new ArrayList<Item>();
		
		if(page < 0)
			page = 0;
		
		if(limit < 0)
			limit = Constant.REST_LIMIT;
		
		int skip = Constant.REST_LIMIT*page;
		Criteria c =null;
		
		// if tag is not  null or not empty 
		if(!StringUtils.isEmpty(tag))
			c= Criteria.where("tag").is(tag);
					
		// check the sort is within the sort types
		if(!Constant.sortby.contains(sort))
			sort = Constant.default_sort;
		
		String sortBy = "date";
		if(sort.equals(Constant.newest))
		{
			sortBy = "date";
			if(c== null)
				c = Criteria.where("date").lte(new Date());
			else
				c.and("date").lte(new Date());
		}
		else
		{
			sortBy = "total";
			Date now = new Date();
			Date date1 = null;
			if(Constant.weekly.equals(sort))
			{
				date1 = PicUtil.getDateBefore(now, 30); // one week ago
			}
			else if(Constant.monthly.equals(sort))    
			{
				date1 = PicUtil.getDateBefore(now, 30); // one month ago
			}
			else if(Constant.hottest.equals(sort))
			{
				date1 = PicUtil.getDateBefore(now, 30*12*10); //ten year ago
			}
			
			if(c== null)
				c = Criteria.where("date").gte(date1);
			else
				c.and("date").gte(date1);
		}
		
		Query query = new Query(c);
		query.sort().on(sortBy, Order.DESCENDING);
		query.skip(skip);
		query.limit(limit);
		
		
		
		return this.mongoOps.find(query, Item.class);
	}
	
	public Item getNextItem(String tag, String id,String sort,int skip) 
	{
		
		if(skip > Constant.MAX_SKIP*Constant.REST_LIMIT || skip < 0)
			skip = 0;
		
		// check the sort is within the sort types
		if(!Constant.sortby.contains(sort))
			sort = Constant.default_sort;
		
		String sortBy = "date";
		Date date1 = PicUtil.getDateBefore(new Date(), 30*12*10);
		if(sort.equals(Constant.newest))
		{
			sortBy = "date";
		}
		else
		{
			sortBy = "total";
			Date now = new Date();
			if(Constant.weekly.equals(sort))
			{
				date1 = PicUtil.getDateBefore(now, 30); // one week ago
			}
			else if(Constant.monthly.equals(sort))    
			{
				date1 = PicUtil.getDateBefore(now, 30); // one month ago
			}
			else if(Constant.hottest.equals(sort))
			{
				date1 = PicUtil.getDateBefore(now, 30*12*10); //ten year ago
			}
			
			
		}
		Criteria c =  Criteria.where("tag").is(tag).and("id").ne(id).and("date").gte(date1);
		Query query = new Query(c);
		query.sort().on(sortBy, Order.DESCENDING);
		query.skip(skip);
		return this.mongoOps.findOne(query, Item.class);
		
	}
	
	public Item getPreItem(String tag, String id,String sort,int skip) 
	{
		return this.getNextItem(tag, id, sort, skip);
	}


	
}
