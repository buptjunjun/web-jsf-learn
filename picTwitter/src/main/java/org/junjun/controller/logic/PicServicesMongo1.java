package org.junjun.controller.logic;

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

public class PicServicesMongo1 implements PicServices{

	public static final  String dbname = "picdb";
	public static final int  MAXLIMT = 20;
	public static final int COMMENTLIMIT = 10;
	private Buffer buffer = null;
	@Autowired
	private DAOMongo mongo;
	public PicServicesMongo1() {
		mongo  = new DAOMongo("42.96.143.59",27017,dbname);
		buffer = new Buffer();
	}
	  
	@Override
	public List<Tag> getTag() {
		// TODO Auto-generated method stub
		return mongo.search(null, null, null, null, -1,MAXLIMT , Tag.class);
	}
	
	@Override
	public User getUser(String id) 
	{
		Map<String,String> constrainEQ = new  HashMap<String,String>();
		constrainEQ.put("id", id);
		List<User> items = this.mongo.search(null, null, constrainEQ, null, -1, 1, User.class);
		
		if(items!=null && items.size() > 0)
		{
			User user= items.get(0);			
			return user;
		}
		
		return null;
	}
	
	@Override
	public void updateUser(User user) {
		if(user == null)
			return ;
		
		Map<String,String> constrainEQ = new  HashMap<String,String>();
		constrainEQ.put("id", user.getId());
		this.mongo.update(user, constrainEQ);
	}
	@Override
	public List<UIComment> getUIComments(String id) {
		// TODO Auto-generated method stub		
		List<UIComment> uicomments = new ArrayList<UIComment>();
		Map<String,String> constrainEQ = null;
		if(id !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("commentTo",id);
			List<Comment> comments = this.mongo.search(null, null, constrainEQ, "date",DAOMongo.DESCENDING, COMMENTLIMIT, Comment.class);
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
	
	/**
	 * items whosetype is "type",time before date;
	 */
	
	public List<Item> getNewestItems(String type,Date date, int limit) {
		// TODO Auto-generated method stub
		Map<String,String> constrainEQ = null;
		if(type !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("type",type);
		}
		Map<String,Date> constrainLT = null;
		if(date!=null)
		{
			constrainLT = new  HashMap<String,Date>();
			constrainLT.put("date", date);
		}
		
		if(limit < 0 )
			limit = MAXLIMT;
		return  mongo.search(constrainLT,null , constrainEQ, "date", DAOMongo.DESCENDING, limit, Item.class);
	}
	
	/**
	 * items whosetype is "type",time brefore date, rating lower than "rating";
	 */
	public List<Item> getTopItemByTime(String type, Date dategt,Date datelt, int rating, int limit) {
		Map<String,String> constrainEQ = null;
		if(type !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("type", type);
		}
		
		
		Map<String,Object> constrainGT = null;
		Map<String,Object> constrainLT = null;
		
		if(dategt!=null)
		{
			if(constrainGT == null)
				constrainGT = new  HashMap<String,Object>();
			constrainGT.put("date", dategt);
		}
		
		if(datelt!=null)
		{
			if(constrainLT == null)
				constrainLT = new  HashMap<String,Object>();
			
			constrainLT.put("date", datelt);
		}
		
		if(rating > 0)
		{
			constrainLT = new  HashMap<String,Object>();
			constrainLT.put("total", rating);
		}
	
		
		if(limit < 0 )
			limit = MAXLIMT;
		
		return  mongo.search(constrainLT,constrainGT , constrainEQ, "date", DAOMongo.DESCENDING, limit, Item.class);
	}
	
	public List<Item> getTopItemByTimeAsend(String type, Date dategt,Date datelt, int rating, int limit) {
		Map<String,String> constrainEQ = null;
		if(type !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("type", type);
		}
		
		
		Map<String,Object> constrainGT = null;
		Map<String,Object> constrainLT = null;
		
		if(dategt!=null)
		{
			if(constrainGT == null)
				constrainGT = new  HashMap<String,Object>();
			constrainGT.put("date", dategt);
		}
		
		if(datelt!=null)
		{
			if(constrainLT == null)
				constrainLT = new  HashMap<String,Object>();
			
			constrainLT.put("date", datelt);
		}
		
		if(rating > 0)
		{
			constrainLT = new  HashMap<String,Object>();
			constrainLT.put("total", rating);
		}
	
		
		if(limit < 0 )
			limit = MAXLIMT;
		
		return  mongo.search(constrainLT,constrainGT , constrainEQ, "date", DAOMongo.ASCENDING, limit, Item.class);
	}
	
	
	public List<Item> getTopItemByTimeDesd(String type, Date dategt,Date datelt, int rating, int limit) {
		Map<String,String> constrainEQ = null;
		if(type !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("type", type);
		}
		
		
		Map<String,Object> constrainGT = null;
		Map<String,Object> constrainLT = null;
		
		if(dategt!=null)
		{
			if(constrainGT == null)
				constrainGT = new  HashMap<String,Object>();
			constrainGT.put("date", dategt);
		}
		
		if(datelt!=null)
		{
			if(constrainLT == null)
				constrainLT = new  HashMap<String,Object>();
			
			constrainLT.put("date", datelt);
		}
		
		if(rating > 0)
		{
			constrainLT = new  HashMap<String,Object>();
			constrainLT.put("total", rating);
		}
	
		
		if(limit < 0 )
			limit = MAXLIMT;
		
		return  mongo.search(constrainLT,constrainGT , constrainEQ, "date", DAOMongo.DESCENDING, limit, Item.class);
	}
	
	public List<Item> getTopItemByTime(String type, Date dategt,Date datelt, int rating, String sortField, int sortWay,int limit) {
		Map<String,String> constrainEQ = null;
		if(type !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("type", type);
		}
		
		
		Map<String,Object> constrainGT = null;
		Map<String,Object> constrainLT = null;
		
		if(dategt!=null)
		{
			if(constrainGT == null)
				constrainGT = new  HashMap<String,Object>();
			constrainGT.put("date", dategt);
		}
		
		if(datelt!=null)
		{
			if(constrainLT == null)
				constrainLT = new  HashMap<String,Object>();
			
			constrainLT.put("date", datelt);
		}
		
		if(rating > 0)
		{
			constrainLT = new  HashMap<String,Object>();
			constrainLT.put("total", rating);
		}
	
		
		if(limit < 0 )
			limit = MAXLIMT;
		
		return  mongo.search(constrainLT,constrainGT , constrainEQ, sortField, sortWay, limit, Item.class);
	}
	
	@Override
	public void insertComment(Comment comment) {
		// TODO Auto-generated method stub
		this.mongo.insert(comment);
		Item item = this.getItem(comment.getCommentTo());
		item.setComment(item.getComment()+1);		
		this.updateItem(item);
	}
	@Override
	public void insertItem(Item item) {
		this.mongo.insert(item);
	}
	
	@Override
	public Item getItem(String id) {
		Map<String,String> constrainEQ = new  HashMap<String,String>();
		constrainEQ.put("id", id);
		List<Item> items = this.mongo.search(null, null, constrainEQ, null, -1, 1, Item.class);
		
		if(items!=null && items.size() > 0)
		{
			Item item= items.get(0);			
			return item;
		}
		
		return null;
		
	}
	public void updateItem(Item item) 
	{
		if(item == null)
			return;
		
		Map<String,String> constrainEQ = new  HashMap<String,String>();
		constrainEQ.put("id", item.getId());
		this.mongo.update(item, constrainEQ);
	}
	
	@Override
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		this.mongo.insert(obj);
	}
	
	

	public List<Item> getItemsWhenLoad(String type, String kind) 
	{
		int rating = Integer.MAX_VALUE;
		// TODO Auto-generated method stub
		List<Item> ret = null;
		

		Tag tag = null;
		if(!buffer.containTag(type))
			type = buffer.getTags().get(0).getType();
		
		if(!Constant.kinds.contains(kind))
			kind=Constant.daily;

		// newest item
		if(kind.contains(Constant.newest))
		{
		ret = this.getNewestItems(type, new Date(), Buffer.BUFFERLIMIT);
		}
		else
		{
			Date datebefore = null;
			if(kind.contains(Constant.daily))
			{
				// the date of newest Item
				Date newest = Buffer.getNewestItem().getDate();
				 datebefore = PicUtil.getDateBefore(newest, 1);
				//this.getTopItemByTime(type, datebefore, null, -1, Buffer.BUFFERLIMIT);
			}
			else if(kind.contains(Constant.weekly))
			{
				Date newest = Buffer.getNewestItem().getDate();
				datebefore = PicUtil.getDateBefore(newest, 7);
			}
			else if(kind.contains(Constant.monthly))
			{
				Date newest = Buffer.getNewestItem().getDate();
				datebefore = PicUtil.getDateBefore(newest, 30);
			}
			
			ret = getTopItemByTime(type,datebefore,null, -1,"total",DAOMongo.DESCENDING ,Buffer.BUFFERLIMIT*3) ;
		}
		
		return ret;
	}
	

	public List<Item> getItemsWhenRest(String id, String kind ) 
	{
		Item item = this.getItem(id);
		
		if(item == null)
		{
			System.out.println("getItemsWhenRest: item == null");
			return null;
		}
		
		// TODO Auto-generated method stub
		String type = item.getType();
		Date date = item.getDate();
		
		List<Item> ret = null;
		
		Tag tag = new Tag();
		tag.setType(type);
		if(!buffer.getTags().contains(tag)||!Constant.kinds.contains(kind))
			return null;
			
		if(kind.equals(Constant.newest))
			ret = this.getTopItemByTimeDesd(type,null,date, item.getTotal(), Buffer.BUFFERLIMIT);
		/*else
		   ret = this.getTopItemByTime(type,null,date, item.getTotal(), Buffer.BUFFERLIMIT);*/
		return ret;
		
	}
	
	

	public List<Item> getItemsWhenIndexRest(String id) {
		Item item = this.getItem(id);
		
		if(item == null)
		{
			System.out.println("getItemsWhenIndexRest: id="+id+"item == null ");
			return null;
		}
		
		// TODO Auto-generated method stub
		List<Item> items  =null;
		items = this.getTopItemByTime(item.getType(),null,item.getDate(), -1,"date",DAOMongo.DESCENDING, Buffer.BUFFERLIMIT);
		return items;

	}
	
	

	public List<Item> getItemsWhenLoadIndx() {
		// TODO Auto-generated method stub
		List<Item> items  =null;
		items = this.getTopItemByTime(null,null,new Date (), -1, Buffer.BUFFERLIMIT);
		return items;
	}
	
	@Override
	public Item getNextItem(String id, String type, String kind) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Item getPreItem(String id, String type, String kind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItemByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItemByTagRest(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItemByTagAndKind(String tag, String kind) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
