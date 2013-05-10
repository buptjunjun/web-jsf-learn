package com.junjun.algorithm.charpter7;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;

/**
 * 线性排序相关内容 桶排序 
 */

public class BucketSort 
{
	
	//辅助类
	class Elem
	{
		int value;
		Elem next = null;
		
		public Elem(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	/**
	 * 桶排序 是线性时间排序的一种，但是需要较多的额外内存,并且需要一些较强的条件：比如需要知道待排序元素都在某一个范围 比如[0,k],并且带排数列呈现均匀分布
	 * 桶排序是将 带排元素分为几个范围，在这个范围内进行排序，然后再组合起来
	 * 比如
	 * 待排数列                        非配到桶中						 按顺序搜集			
	 * 78           [0-10]    
	 * 17			[11-20]->12->17
	 * 39			[21-30]->21->23->26
	 * 26			[31-40]->39	
	 * 72     ->	[41-50]                -> 12 17 21 23 26 39 68 72 94
	 * 94			[51-60]
	 * 21			[61-70]->68
	 * 12			[71-80]->72
	 * 23			[81-90]
	 * 28			[91-100]->94
	 *   
	 * @param A        待排数组
	 * @param maxElem  数组中所有元素都小于等于与maxElem
	 * @param minElem  数组中所有元素都大于等于呢个与maxElem 
	 * @param interval 怎么分配木桶的间隔
	 * @return
	 */
	public int [] sort(int A[],int minElem, int maxElem,int interval)
	{
		// 桶的个数
		int num = (maxElem-minElem)/interval+1;
		
		//初始化所有木桶为null
		Elem [] buckets = new Elem[num];
		Arrays.fill(buckets, null);
		
		//将每一个元素分配到每一个桶中
		for(int i = 0; i < A.length; i++)
		{
			// 将A[i]分配到第[(A[i]-minElem)/10]个桶中
			int which = (A[i]-minElem)/10;
			Elem bucket = new Elem(A[i]) ;
			
			if(buckets[which] == null)
			{		
				buckets[which]= bucket;
			}
			else
			{
				Elem head = buckets[which];
				while(A[i] < head.getValue() && head.next != null)
				{
					head = head.next;
				}
				
				//找到插入点head,并插入在head后
				Elem tmp = head.next;
				head.next = bucket;
				bucket.next = tmp;
				
			}
		}
		
		// 按顺序搜集每一个桶
		int j = 0;
		for(int i=0; i < buckets.length; i++ )
		{
			//取得一个木桶并且搜集所有木桶的元素
			Elem head = buckets[i];
			while(head!=null)
			{
				A[j++] = head.getValue();
				head = head.next;
			}
		}
		
		return A;
		
	}
	
	
	public static void main(String [] args)
	{
		int [] A = { 72,26,12, 17, 21, 23, 39, 68, 94};
		BucketSort cs = new BucketSort();
		print(A, 0,A.length-1,"original array ");
		int [] B = cs.sort(A, -1,100,5);
		print(B, 0,B.length-1,"BucketSort array ");
	
		

	}
	
	static void print(int [] A,int from,int to,String msg)
	{	
		System.out.print(msg+" \n");
		for(int i = from; i<=to; i++)
		{
			System.out.print(A[i]+" ");
		}
		System.out.print("\n");
	}
}
