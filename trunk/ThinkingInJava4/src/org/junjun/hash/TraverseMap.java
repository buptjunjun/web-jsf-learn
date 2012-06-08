package org.junjun.hash;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 遍历map的两种方法
 * @author andyWebsense
 *
 */
public class TraverseMap 
{
	/*
	 *  the first method to traverse a map,this method has higher efficiency
	 */
	public static void traverse1(Map map)
	{
		if(map == null)
		{
			System.out.println("map == null");
			return;
		} 
		
		Iterator<Map.Entry<?, ?>> i = (Iterator<Map.Entry<?, ?>>) map.entrySet().iterator();
		while(i.hasNext())
		{
			Map.Entry<? ,?> entry = i.next();
			System.out.print(entry.getKey() +","+entry.getValue()+"|     ");		
		}
		System.out.println();
	}
	
	/*
	 *  the second method to traverse a map, this one have lower efficiency
	 */
	public static void traverse2(Map map)
	{
		if(map == null)
		{
			System.out.println("map == null");
			return;
		} 
		
		Set<String> keys = map.keySet();
		for(String key: keys)
		{
			 Object value = map.get(key);
			 System.out.print(key+","+ value+"|     ");		
		}
		System.out.println();
	}
	
	public static void main(String [] args)
	{	 
		  // create a map 
		  String [] str = "yang ting jun ".split(" ");
		  Map map = new HashMap();
		  for ( int i = 0; i < str.length; i++)
		  {
			  	map.put(str[i], str[i]+" haha");
		  }
		  
		  //traverse the map using the two method
		  traverse1(map);
		  traverse2(map);
	}
}
