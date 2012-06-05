package org.junjun.holdObject;


import java.util.*;

/**
 * map 打印自己 , collection 和 map 都有很友好的打印自己的 方式
 * @author andyWebsense
 *
 */
public class PrintContainer 
{
	static Collection fill(Collection<String> collection)
	{
		collection.add("rat");
		collection.add("cat");
		collection.add("dog");
		collection.add("dog");
		collection.add("pig");
		collection.add("bird");
		collection.add("antelope");
		return collection;
	}
	
	static Map fill(Map<String ,String> map)
	{
		map.put("rat", "Fuzzy");
		map.put("cat", "Rags");
		map.put("dog", "Bosco");
		map.put("dog", "Spot");
		map.put("pig", "Bosco");
		map.put("pigen", "Spot");
		map.put("bird", "Bosco");
		map.put("antilope", "Spot");
		return map;
	}
	
	static public void main(String [] args)
	{
		System.out.println(fill(new ArrayList<String>()));
		System.out.println(fill(new LinkedList<String>()));
		System.out.println(fill(new HashSet<String>())); //乱序的
		System.out.println(fill(new TreeSet<String>())); //TreeSet 是按照升序来排序的
		System.out.println(fill(new LinkedHashSet<String>())); // linkedHashSet按照add的顺序来保持而起保存了 hashset的有点
		


	}

}
