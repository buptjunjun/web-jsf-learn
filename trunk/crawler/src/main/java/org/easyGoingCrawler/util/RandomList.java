package org.easyGoingCrawler.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomList
{
	static public void random(List l)
	{
		if (l == null || l.size() <= 0) return; 
		
		int size = l.size();
		
		for(int i = 0; i < size; i++)
		{
			int positon = Math.abs(new Random().nextInt()%size);		
			// exchange 
			Object hold = l.get(i);
			l.set(i, l.get(positon));
			l.set(positon,hold);				
		}
		
	}
	
	static public void main(String args [])
	{
		List l = Arrays.asList("1,2,3,4,5,6,7,8,9,0".split(","));
		System.out.println(l);
		random(l);
		System.out.println(l);
	}
}
