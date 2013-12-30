package com.buptjunjun.collectionsTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.buptjunjun.annotation.Test;

public class CollectionsToolTest {

	/**
	 * test Collections.unmodifiableMap(map);
	 */
	public static  void Test_unmodifiableMap()
	{
		 ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
		 map.put("a", "a");		
		
		 Map<String,String> unmodifiablemap = Collections.unmodifiableMap(map);	
		 System.out.println(unmodifiablemap);
		 
		 // if add new values to map, it will show in unmodifiablemap
		 map.put("c", "c");
		 System.out.println(unmodifiablemap);
		 
		 // this will throw java.lang.UnsupportedOperationException
		/* try
		 {
			 unmodifiablemap.put("b", "b");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }*/
		 
		
	}
	
	
	/**
	 * test CopyOnWriteArrayList also get issues
	 */
	public static  void Test_CopyOnWriteArrayList()
	{
		 
		final List<Integer> cowList = new ArrayList<Integer>();
		
		for(int i=0;i<10;i++)
		{
			cowList.add(i);
		}
		
		new Thread(){
			@Override
			public void run() {
				for(Integer i:cowList)
				{
					try {
						Thread.currentThread().sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(cowList.get(i));
				}
				Iterator<Integer> it = cowList.iterator();
				while(it.hasNext())
				{
					try {
						Thread.currentThread().sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(it.next());
				}
			};
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				try {
					Thread.currentThread().sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cowList.remove(1);
				cowList.add(1);
				try {
					Thread.currentThread().sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//remove will cause java.lang.ArrayIndexOutOfBoundsException:
				//cowList.add(11);
				
			};
		}.start();
		
	}

	
	/**
	 * 
	 * @param args
	 */
	
	public static void main(String [] args)
	{
		Test_unmodifiableMap();
		Test_CopyOnWriteArrayList();
		
	}	
	

}



