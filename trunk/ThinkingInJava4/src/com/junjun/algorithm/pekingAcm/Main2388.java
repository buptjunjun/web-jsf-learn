package com.junjun.algorithm.pekingAcm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
* 
*  快速排序变种，找第k大的值 O(n)
* 
*/


public class Main2388
{
	static public Random random = new Random();
	/**
	 * 
	 * @param array
	 * @param x
	 * @param y
	 */
	static void swap(int []array,int x,int y)
	{
		int tmp = array[x];
		array[x] = array[y];
		array[y] = tmp;
	}
	
	/**
	 * 分割
	 * @param array
	 * @param position
	 * @return
	 */
	static public int  partition(int [] array,int begin,int end,int position)
	{
		swap(array,begin,position);
		int povit = array[begin];
		int i = begin;
		int border = begin+1;
		for(i = begin;i <= end; i++)
		{
			if(array[i]<povit)
			{
				swap(array,border,i);
				border++;
			}		
		}
		
		swap(array,border-1,begin);
		
		return border-1;
	}
	
	/**
	 *  在数组array[from,to]中找到一个第k小的元素 
	 * rarrayndom-select 算法：
	 * 类似于快速排序中的随机选择一个pivot=p,是pivot左边的都比pivot小 array[from,]，pivot右边的都比pivot大
	 * 如果 pivot左边(加上pivot)恰好有k个数，则 返回pivot 因为他是第k大的
	 * 如果pivot左边(加上pivot)有n个数 ，n>k对pivot左边的元素递归调用这个算法
	 * 如果pivot左边(加上pivot)有n个数n<k,说明现在我们知道了n个最小的值了，只需要递归第在pivot右边找第（k-n）个最小值即可
	 * 算法时间复杂度为o(n)
	 * 
	 * @parrayram array
	 * @param begin
	 * @param end
	 * @param kth
	 * @return
	 */
	public static int findNthMin(int [] array,int begin, int end,int kth)
	{
		if(begin == end)
			return array[begin];
		int pos = random.nextInt(end-begin+1)+begin;
		
		int povitPos = partition(array,begin,end,pos);
		int ith=povitPos - begin +1;
		if(ith == kth)
			return array[povitPos];
		else if(ith>kth)
		{
			return findNthMin(array,begin,povitPos-1,kth); 
		}
		else
		{
			return findNthMin(array,povitPos+1,end,kth-ith); 
		}
	}
	
	public static void main(String[] args)
	{
		
		Scanner scan = new Scanner(System.in);	
		int n  = scan.nextInt();
		int [] array = new int[n+1];
		for(int i = 0;i < n; i++)
		{
			array[i+1] = scan.nextInt();
		}
		scan.close();
		
		int ret =findNthMin(array,1,array.length-1,array.length/2);
		System.out.println(ret);
		
	}
}