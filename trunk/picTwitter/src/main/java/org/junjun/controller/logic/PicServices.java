package org.junjun.controller.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.bean.part1.UIItem;

public interface  PicServices 
{
	public List<Tag> getTag();
	
	public List<Item> getTopItemByTime(String type,Date date, int rating, int limit);	
	public List<Item> getNewestItems(String type,Date date,int limit);	
	public UIItem getUIItem(String id);	
	public Item getItem(String id);
	
	public void updateItem(Item item);
	public void insertItem(Item item);
	public void insertComment(Comment item);
	
	public void insert(Object obj);

}
