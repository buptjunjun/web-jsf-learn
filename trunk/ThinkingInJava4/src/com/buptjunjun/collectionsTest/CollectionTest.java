package com.buptjunjun.collectionsTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * java 中 map collections iterator的关系
 * map可以生成 collection,collection可以生成iterator
 * @author andyWebsense
 *
 */
public class CollectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map map = new HashMap();
		
		// 从map生成collection; set属于collection
		Collection set = map.entrySet();
		
		// 从collection 生成 Iterator
		Iterator i = set.iterator();
		
		System.out.println(new Timestamp(10000));
		
	}

}
