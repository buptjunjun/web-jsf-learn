package org.junjun.holdObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class AddingGroup 
{
	public static void main(String [] args)
	{
		Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
		printCollection(collection, "------------1-------------");
		Integer [] moreInts = {11,22,33,44};
		collection.addAll(Arrays.asList(moreInts));
		printCollection(collection, "------------2-------------");	
		Collections.addAll(collection, 55,66);
		printCollection(collection, "------------3-------------");		
		Collections.addAll(collection, moreInts);
		printCollection(collection, "------------4-------------");	
		
		List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);
		list.set(1, 99);
		//asList 返回的不能修改大小，不能add 和delete
		/*list.add(new Integer(21));*/
	}
	
	public static void printCollection(Collection c,String info)
	{
		System.out.printf(info);
		
		int m = 0;
		Iterator i = c.iterator();
		for(;i.hasNext();)
		{
			Integer num = (Integer)i.next();
			System.out.print(num+" ");
		}
		
		System.out.println();
	}
}
