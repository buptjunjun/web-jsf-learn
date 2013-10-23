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

public class PicServicesMongo implements PicServices{

	public static final  String dbname = "picdb";
	public static final int  MAXLIMT = 20;
	public static final int COMMENTLIMIT = 10;
	private Buffer buffer = null;
	@Autowired
	private DAOMongo mongo;
	public PicServicesMongo() {
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
	 * 
	 * @param type   cute  funny or ....s
	 * @param dategt  date > dategt
	 * @param datelt  date < datelt
	 * @param rating  rating is the score of a item
	 * @param sortField  sort by sortField
	 * @param sortWay
	 * @param limit
	 * @return
	 */
	public List<Item> getItem(String type, Date dategt,Date datelt, int rating, String sortField, int sortWay,int limit) {
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
	


	

	@Override
	public List<Item> getItemByTag(String tag) {
		if(!Buffer.containTag(tag))
			tag = null;		
		List<Item> ret = this.getItem(tag, null, null, -1, "date", this.mongo.DESCENDING, Buffer.BUFFERLIMIT*3);
		return ret;
	}
	
	@Override
	public List<Item> getItemByTagRest(String tag, String id) {
		Item item = this.getItem(id);
		if(id == null)
			return null;
		
		if(tag != null)
			tag = item.getType();
		List<Item> ret = this.getItem(tag,null, item.getDate(), -1, "date", this.mongo.DESCENDING, Buffer.BUFFERLIMIT);
	    return ret;
	}
	
	@Override
	public List<Item> getItemByTagAndKind(String tag, String kind) {
		if(!Buffer.containTag(tag))
			tag = null;		
		if(!Constant.kinds.contains(kind))
			kind = Constant.defaultKind;
		
		Date newestDate = Buffer.getNewestItem().getDate();
		int dayBefore = 1;
		if(Constant.daily.equals(kind))
		{
			dayBefore = 1;
		}
		else if (Constant.weekly.equals(kind))
		{
			dayBefore = 7;
		}
		else if (Constant.monthly.equals(kind))
		{
			dayBefore = 30;
		}
		
		Date date = PicUtil.getDateBefore(newestDate, dayBefore);
		List<Item> ret = this.getItem(tag, date, null, -1, "total", this.mongo.DESCENDING, Buffer.BUFFERLIMIT*3);
		return ret;
	}

	@Override
	public Item getNextItem(String id) 
	{
		Item item = this.getItem(id);
		if(id == null)
			return null;
		
		List<Item> ret = this.getItem(item.getType(),null, item.getDate(), -1, "date", this.mongo.DESCENDING, 1);
		if(ret == null || ret.size() == 0)
		{
			ret = this.getItem(item.getType(), null,new Date(), -1, "date", this.mongo.DESCENDING, 1);
		}
		
		if(ret == null || ret.size() != 1)
			return null;
		
		return ret.get(0);
		
	}
	@Override
	public Item getPreItem(String id) {

		Item item = this.getItem(id);
		if(id == null)
			return null;
		
		List<Item> ret = this.getItem(item.getType(),item.getDate(),null,  -1, "date", this.mongo.ASCENDING, 1);
		
		if(ret == null || ret.size() != 1)
			return null;
		
		return ret.get(0);
	}
	

	
}
