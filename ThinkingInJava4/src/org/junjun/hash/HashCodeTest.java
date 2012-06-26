package org.junjun.hash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 重写hashcode
 * @author andyWebsense
 *
 */
class People
{
	String name = null;
	public People(String name)
	{
		this.name = name;
	}
	
	/**
	 * 重写equals
	 */
	public boolean equals(Object obj) 
	{
		if(obj == null)
			return false;
		else
		{
			if(this.name != null)
				return this.name.equals(((People)obj).name);
			else if(this.name == null && ((People)obj).name == null)
				return true;
			else
				return false;
		}
	}
	
	/**
	 * 重写hashCode
	 */
	public int hashCode() {
		// TODO Auto-generated method stub
		if(this.name != null)
			return this.name.hashCode();
		else return 0;
	}
}

public class HashCodeTest
{
	static public void main(String [] args)
	{
		List<People> plist = new ArrayList<People>();
		Collection<People> hlist = new HashSet<People>();

		String [] people = "wan,xiao,lan,wan,wan".split(",");
		for(int i = 0; i < people.length; i++)
		{
			plist.add(new People(people[i]) );
			hlist.add(new People(people[i]) );
		}
		
		hlist.add(new People(null));
		hlist.add(new People(null));
		hlist.add(null);
		hlist.add(null);
		
		// 打印hashcode
		for(People p: plist)
			System.out.println(p.name +"   " + p.hashCode()+"");
				
		System.out.println("-------------"+hlist.size()+"-----------------");
		
		//打印HashSet的情况
		for(People p: hlist)
		{
			if (p != null)
			    System.out.println(p.name + ",  " + p.hashCode() + "--" );
			else 
			    System.out.println(p+"--" );			
		}
		
		Integer[] ii = new Integer[0];
		
		HashMap testmap = new HashMap();
		testmap.put("1", "aaaa");
		testmap.put("2", new String [] {"123","222"});
		testmap.put("1", "bbbbb");
		System.out.println(testmap);
		
		HashMap hclone = (HashMap) testmap.clone();
		String [] strs = (String[]) testmap.get("2");
		System.out.println(strs.length);
	}
}
