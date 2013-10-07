package org.junjun.controller.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.bean.part1.UIItem;
import org.junjun.mongo.DAOMongo;
import org.springframework.beans.factory.annotation.Autowired;

public class PicServicesMongo implements PicServices{

	private static final  String dbname = "picdb";
	public static final int  MAXLIMT = 30;
	
	@Autowired
	private DAOMongo mongo;
	public PicServicesMongo() {
		mongo  = new DAOMongo("42.96.143.59",27017,dbname);
	}
	  
	@Override
	public List<Tag> getTag() {
		// TODO Auto-generated method stub
		return mongo.search(null, null, null, null, -1,MAXLIMT , Tag.class);
	}


	@Override
	public UIItem getUIItem(String id) {
		// TODO Auto-generated method stub
		Map<String,String> constrainEQ = null;
		if(id !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("id",id);
			List<Item> item = this.mongo.search(null, null, constrainEQ, null, -1, 1, Item.class);
			if(item!=null && item.size()>0)
			{
				Item i = item.get(0);
				
				constrainEQ.clear();
				constrainEQ.put("commentTo",id);
				List<Comment> comments = this.mongo.search(null, null, constrainEQ, null, -1, 1, Comment.class);
				
				UIItem uiitem = new UIItem();
				uiitem.setItem(i);
				uiitem.setComments(comments);
				
				return uiitem;
			}
		}
		return null;
	}
	@Override
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
	@Override
	public List<Item> getTopItemByTime(String type, Date date, int rating, int limit) {
		Map<String,String> constrainEQ = null;
		if(type !=null)
		{
			constrainEQ = new  HashMap<String,String>();
			constrainEQ.put("type", type);
		}
		
		Map<String,Date> constrainGT = null;
		if(date!=null)
		{
			constrainGT = new  HashMap<String,Date>();
			constrainGT.put("date", date);
		}
		
		Map<String,Integer> constrainLT = null;
		if(date!=null)
		{
			constrainLT = new  HashMap<String,Integer>();
			constrainLT.put("total", rating);
		}
		
		if(limit < 0 )
			limit = MAXLIMT;
		
		return  mongo.search(constrainLT,constrainGT , constrainEQ, "date", DAOMongo.DESCENDING, limit, Item.class);
	}
	
	@Override
	public void insertComment(Comment comment) {
		// TODO Auto-generated method stub
		this.mongo.insert(comment);
		Item item = this.getItem(comment.getCommentTo());
		item.setComments(item.getComments()+1);		
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
	public void updateItem(Item item) {
		
		Map<String,String> constrainEQ = new  HashMap<String,String>();
		constrainEQ.put("id", item.getId());
		this.mongo.update(item, constrainEQ);
	}
	
	@Override
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		this.mongo.insert(obj);
	}
	
}
