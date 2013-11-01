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
	public Item getItem(String id);
	public List<UIComment> getUIComments(String itemId);
	public User getUser(String id);

	public void insertItem(Item item);
	public void insertComment(Comment item);	
	public void insert(Object obj);
	
	public void updateUser(User user);
	public void updateItem(Item item);
	
	/**
	 * type : animal 
	 * kind : weekly monthly
	 * @param type
	 * @param kind
	 * @return
	 */
	
	public List<Item> getItemByTag(String tag,int page,String sort,int limit);

	public Item getPreItem(String tag, String id,String sort,int skip) ;	
	public Item getNextItem(String tag, String id,String sort,int skip) ;
	
	

}
