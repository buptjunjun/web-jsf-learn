package com.junjun.algorithm.charpter5;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

/**
 * 将一个数组的随机排列
 * @author junjun
 *
 */
public class RandomArray 
{
	private Random  r = new Random();
	 
	/**
	 * 辅助类
	 * score存放随机分数
	 * num存放待排列的数
	 * @author andyWebsense
	 *
	 */
	class Item implements Comparable
	{
		int num = 0;
		long score = 0l;
		@Override
		public boolean equals(Object obj)
		{
			Item item = (Item)obj;
			return item.score == this.score;
		}
		
		@Override
		public int compareTo(Object arg0)
		{
			Item item = (Item)arg0;
			if(this.score > item.score)
				return -1;
			else if (this.score < item.score)
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * 将一个数列打乱为随机数列
	 * 算法：
	 * 1、取得数组的长度n,计算一个上线 upperLimit = n^3
	 * 2、对每一个数组元素生成一个[0,upperLimit]内的随机分数score，每一个随机数不同
	 * 3、按照score对这个数列进行排序(递增，递减均可)
	 * 4、得到随机数列
	 *  时间复杂度为nlogn 因为 排序是nlogn的
	 * @param array
	 * @return 返回一个扰乱以后的数列
	 */
	public int [] disturb1(int [] array)
	{
		//取得数组的长度n,计算一个上线 upperLimit = n^3
		int upperlimit = (int) Math.pow(array.length,3);
		
		HashSet<Item> hset = new HashSet<Item>();
		for(int elem : array)
		{
			Item i = new Item();
			i.num = elem;
			i.score = -1;
			
			// 保证每一个分数score都是 唯一的
			while(true)
			{	 
				int randomScore = r.random0n(upperlimit);
				i.score = randomScore;
				if(!hset.contains(i))
					break;
			}			
			hset.add(i);
		}
		
		//按照分数对数列进行排序
		Object [] arrayItem =  hset.toArray();
		Arrays.sort(arrayItem);
		
		// 返回 扰乱后的数组
		int [] ret = new int[arrayItem.length];
		for(int i = 0;i < arrayItem.length;i++)
		{	
			Item item = (Item)arrayItem[i];
			ret[i] = item.num;
		}
		
		return ret;
			
	}
	
	
	/**
	 * 将一个数列打乱为随机数列
	 * 算法 就是交换第i个元素与i个元素后面的随机的一个元素： 
	 *   设数列为A
	 *   for i <- 1 to length(A)
	 *     swap(A[i],A[Random(i,length(A))])
	 *     
	 *  时间复杂度为nlogn 因为 排序是nlogn的
	 * @param array
	 * @return 返回一个扰乱以后的数列
	 */
	public int [] disturb2(int [] array)
	{
		//取得数组的长度
		int  length = array.length;
		// copy 一份
		int [] tmp = Arrays.copyOf(array, length);
		
		for(int i = 0; i < length-1; i++)
		{
			int random = r.randomab(i, length-1);
			
			//swap(A[i],A[Random(i,length(A))])
			int tmpInt = tmp[random];
			tmp[random] = tmp[i];
			tmp[i] = tmpInt;
		}
		
		return tmp;
			
	}
	
	public static void main(String[] args)
	{
		// 待打乱的数组
		int [] array = {1,2,3,4,5,6,7,8,9};
		print(array,0,array.length - 1 ,"original array");
		
		RandomArray r = new RandomArray();
		int [] ret = r.disturb1(array);
		print(array,0,array.length - 1 ,"disturb1");
		
		ret = r.disturb2(array);	
		print(array,0,array.length - 1 ,"disturb2");
			
	}
	
	public static void print(int [] A,int from,int to,String msg)
	{	
		System.out.print(msg+" \n");
		for(int i = from; i<=to; i++)
		{
			System.out.print(A[i]+" ");
		}
		System.out.print("\n");
	}
}
