package com.junjun.algorithm.charpter5;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

public class TestRandom
{

	@Test
	public void testRandom01()
	{
		System.out.println("-------random01--------");
		Random rand = new Random();
		HashMap<Integer,Integer> ret = new HashMap<Integer,Integer>();
		for(int i = 0; i<100000; i++)
		{   
			//  在调用1000000次中每一个随机数出现的次数
			int randNum= rand.random01();
		
			if(!ret.containsKey((Integer)randNum))
			{
				int total = 1;
				ret.put(randNum, total);
			}
			else
			{
				Integer current = ret.get(randNum);
				current++;
				ret.put(randNum, current);
			}
		}
		
		for(Entry<Integer, Integer> entry:ret.entrySet())
		{
			System.out.println(entry.getKey()+":"+entry.getValue()+" 次");
		}
	}
	
	@Test
	public void testRandom0n()
	{
		System.out.println("-------random0n--------");
		Random rand = new Random();
		HashMap<Integer,Integer> ret = new HashMap<Integer,Integer>();
		for(int i = 0; i<100000; i++)
		{   
			//  在调用1000000次中每一个随机数出现的次数
			int randNum= rand.random0n(6);
		
			if(!ret.containsKey((Integer)randNum))
			{
				int total = 1;
				ret.put(randNum, total);
			}
			else
			{
				Integer current = ret.get(randNum);
				current++;
				ret.put(randNum, current);
			}
		}
		
		for(Entry<Integer, Integer> entry:ret.entrySet())
		{
			System.out.println(entry.getKey()+":"+entry.getValue()+" 次");
		}
		
	}
	
	@Test
	public void testRandomab()
	{
		System.out.println("-------randomab--------");
		Random rand = new Random();
		HashMap<Integer,Integer> ret = new HashMap<Integer,Integer>();
		for(int i = 0; i<100000; i++)
		{   
			//  在调用1000000次中每一个随机数出现的次数
			int randNum= rand.randomab(3,10);
		
			if(!ret.containsKey((Integer)randNum))
			{
				int total = 1;
				ret.put(randNum, total);
			}
			else
			{
				Integer current = ret.get(randNum);
				current++;
				ret.put(randNum, current);
			}
		}
		
		for(Entry<Integer, Integer> entry:ret.entrySet())
		{
			System.out.println(entry.getKey()+":"+entry.getValue()+" 次");
		}
	}

}
