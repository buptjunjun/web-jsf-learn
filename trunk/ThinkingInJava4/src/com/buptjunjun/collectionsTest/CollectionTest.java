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
 * java �� map collections iterator�Ĺ�ϵ
 * map�������� collection,collection��������iterator
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
		
		// ��map����collection; set����collection
		Collection set = map.entrySet();
		
		// ��collection ���� Iterator
		Iterator i = set.iterator();
		
		System.out.println(new Timestamp(10000));
		
	}

}
