package com.junjun.algorithm.pekingAcm;

import java.util.Scanner;

/**
 * 求逆序对的个数：冒泡排序交换的次数等于逆序数的和
 * 采用分治的办法:
 * 例如数组A[p,...,q]
 * 可以这样分治：
 * 求A[p,middle]中逆序数的个数
 * 求A[middle+1,q]中逆序数的个数
 * 再将A[p,middle] 和 A[middle+1,q]merge并求跨这两个子数组的逆序数的个数
 * http://poj.org/problem?id=2299
 *
 */

public class Main2299
{
	/**
	 * merge并求你叙述对
	 * @param array
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static long  merge(long [] array,int x,int y,int z)
	{
		long [] tmp = new long[z-x+1];
		
		//逆序对的个数
		long countInversPair = 0;
		
		int left = x;
		int right = y+1;
		int smaller = x;
		int cur = 0;
		while(left <= y && right <=z)
		{
			if(array[left] <= array[right])
			{
				smaller = left;
				left++;
			}
			else
			{
				smaller = right;
				right++;
				//计算逆序对的个数
				countInversPair+=(y-left+1);
			}	
			
			tmp[cur++] = array[smaller];
		}
		
		while(right <=z)
		{
			tmp[cur++] = array[right++];	
			
		}
		
		while(left <= y)
		{
			tmp[cur++] = array[left++];	
		}
		
		for(int i = x;i <=z ;i++)
		{
			array[i] = tmp[i-x];
		}
		
		return countInversPair;
			
	}
	
	/**
	 * 其实就是merge的过程
	 * @param array
	 * @param x
	 * @param y
	 * @return
	 */
	static public long  countInversPair(long [] array,int x,int y)
	{
		  if(x>=y)
			  return 0;
		 int middle = (x+y)/2;
		 //求array[x,middle]中逆序数的个数
		 long leftPairs = countInversPair(array,x,middle);
		 //求array[middle+1,y]中逆序数的个数
		 long rightPairs = countInversPair(array,middle+1,y);
		 //再将array[x,middle] 和 array[middle+1,y]merge并求跨这两个子数组的逆序数的个数
		 long pairs = merge(array,x,middle,y);
		 
		 //返回array[x,y]中所有你逆序数的个数
		 return pairs+leftPairs+rightPairs;
	}
	public static void main(String[] args)
	{	
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();	
		while(num!=0)
		{
			long [] l = new long[num];
			for(int i = 0;i < num;i++)
			{
				l[i] = scan.nextLong();
			}
			long count = countInversPair(l,0,l.length-1);
			System.out.println(count);
			num = scan.nextInt();
		}
		scan.close();
		
		/*long [] test = {2,3,5,1,6,7};
		merge(test,0,2,test.length-1);
		System.out.println("");*/
	}
}