package org.junjun.controller.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;

public class Buffer extends Thread
{

	 public static final int BUFFERLIMIT = 300;
	 private static Item newestItem = null; 
	 private static List<Item> indexitem =  new ArrayList<Item>();	 
	 private  static List<Tag> tags = new ArrayList<Tag>();	

	 // all the buffered item
	  private  static Map<String,Item> itemBuffer = new HashMap<String,Item>();
	 /*
	  * <typekind,items>
	  * for example :type=cute kind=weekly ==> <cuteweekly,items>
	  * 
	  */
	 private static Map<String,List<Item>> typekindbuffer = new HashMap<String ,List<Item>>();
	 private boolean flag = false;
	
	 public Buffer() 
 	 {
		flag = true;
		this.start();
	 }
	
	 public void addTypeKind(String type, String kind,List<Item> items)
	 {
		 Tag tag = new Tag();
		 tag.setType(type);
		 
		 if(StringUtils.isEmpty(type) || StringUtils.isEmpty(kind)||items == null || items.size() <=0)
			 return;
		 
		 // we don't have such tag currently
		 if(tags == null || !tags.contains(tag) || !Constant.kinds.contains(kind))
			 return;
		 
		 String key = type+kind;
		 
		 // if no such typkind exist create them
		 if(!this.typekindbuffer.containsKey(key))
		 {
			 this.typekindbuffer.put(key, items);
		 }
		 else // if it exist , just add new Items.
		 {
			 List<Item> oldItems = this.typekindbuffer.get(key);
			 for(Item item:items)
			 {
				 if(!oldItems.contains(item))
					 oldItems.add(item);
			 }
		 }
		 
		 for(Item item:items)
		 {
			 if(!itemBuffer.containsKey(item.getId()))
				 Buffer.itemBuffer.put(item.getId(), item);
		 }
	 }
	 
	 public static boolean containTag(String type)
	 {
		 Tag tag = new Tag();
		 tag.setType(type);
		 if(Buffer.getTags()!=null)
			 return Buffer.getTags().contains(tag);
		 
		 return false;
	 }
	 public static List<Tag> getTags() {
		return tags;
	}

	public static void setTags(List<Tag> tags) {
		Buffer.tags = tags;
	}

	public static Map<String, Item> getItemBuffer() {
		return itemBuffer;
	}

	public static void setItemBuffer(Map<String, Item> itemBuffer) {
		Buffer.itemBuffer = itemBuffer;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<Item> getTypeKind(String type,String kind)
	 {
		 String key = type+kind; 
		 return typekindbuffer.get(key);
	 }
	
	 public void clearAll()
	 {
		 this.tags.clear();
		 this.typekindbuffer.clear();
	 }
	 
	 public static Item getNewestItem() {
			return newestItem;
		}

		public static void setNewestItem(Item newestItem) {
			Buffer.newestItem = newestItem;
		}
		
		 public static List<Item> getIndexitem() {
				return indexitem;
			}

			public static void setIndexitem(List<Item> indexitem) {
				Buffer.indexitem = indexitem;
			}
	 @Override
	public void run() {
		
		 // update clear and update the buffer every 24 hours
		 while(flag)
		 {
			 this.clearAll();		 
			 try 
			 {
				TimeUnit.HOURS.sleep(24);
			 } 
			 catch (InterruptedException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
}
