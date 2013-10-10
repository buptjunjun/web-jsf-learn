package org.junjun.controller.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;

public class PicBuffer
{
	
	//<type,list>
	static public Map<String,List<Item>> itemsNewest = null;
	static public Map<String,List<Item>> itemsHottestWeekly = null;
	static public Map<String,List<Item>> itemsHottestMonthly= null;	
	static public List<Tag> tags = null;
	
	public static Map<String,Item> itemBuffer = new HashMap<String,Item>();
	
}
