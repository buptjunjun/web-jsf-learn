package org.junjun.controller.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.bean.part1.UIComment;
import org.junjun.bean.part1.User;

public interface  PicServices 
{
	public List<Tag> getTag();
	
	public List<Item> getTopItemByTime(String type,Date date, int rating, int limit);	
	public List<Item> getNewestItems(String type,Date date,int limit);	
	public List<UIComment> getUIComments(String itemId);
	public Item getItem(String id);
	public void updateItem(Item item);
	
	public User getUser(String id);
	public void updateUser(User user);
	
	
	public void insertItem(Item item);
	public void insertComment(Comment item);
	
	public void insert(Object obj);
	
	/**
	 * type : animal 
	 * kind : weekly monthly
	 * @param type
	 * @param kind
	 * @return
	 */
	public List<Item> getItemsWhenLoad(String type, String kind);
	public List<Item> getItemsWhenRest(String id, String kind ) ;
	
	public List<Item> getItemsWhenLoadIndx();
	public List<Item> getItemsWhenIndexRest(String id) ;
	
	public Item getNextItem(String id,String type,String kind);	
	public Item getPreItem(String id,String type,String kind);
	
	public List<Item> getTopItemByTime(String type, Date dategt,Date datelt, int rating, int limit) ;

}
