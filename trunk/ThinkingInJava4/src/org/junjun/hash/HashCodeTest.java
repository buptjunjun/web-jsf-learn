package org.junjun.hash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

class People
{
	String name = null;
	public People(String name)
	{
		this.name = name;
	}
	
	@Override
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
	
	@Override
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
		try
		{
			String [] people = "wan,xiao,lan,wan,wan".split(",");
			for(int i = 0; i < people.length; i++)
			{
				plist.add(new People(people[i]));
				hlist.add(new People(people[i]) );
			}
			
			hlist.add(new People(null));
			hlist.add(new People(null));
		
			
			for(People p: plist)
			{
				System.out.println(p.hashCode()+"");
				
			}
			
			System.out.println("-------------"+hlist.size()+"-----------------");
			
			for(People p: hlist)
			{
				if(p!=null)
				System.out.println(p.name + ",  " + p.hashCode() + "--" );
				else System.out.println(p+"--" );			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
