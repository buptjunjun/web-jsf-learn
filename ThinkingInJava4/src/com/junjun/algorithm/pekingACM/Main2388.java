package com.junjun.algorithm.pekingACM;

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
	 * 	  将一个数组A[m,n] 分解，使得A[pivot]的左边的值都小于A[pivot]，A[pivot]的右边的值都大于于A[pivot]。
	 *  比如A=[1,2,5,0,3,6] pivot=4 A[pivot] = 3.
	 *  最后的到的结果为[1,2,0,3,5,6] 返回 3
	 *  
	 *  算法：交换A[pivot] <-> A[0]
	 *      Pivot = A[0]
	 *      i=0
	 *      for j<- 1 to A.length
	 *          if A[j] < A[0]
	 *             i++
	 *             A[j] <-> A[i]
	 *      A[i++] <-> A[0]
	 *      
	 *      例如：
	 *      [4,2,8,7,1,3,5,6]
	 *      如果pivot为A[0]=4
	 *      第一步：border=0,j=1 A[j] < A[0] 那么 border=border+1 = 1, 交换A[j]<->A[border](A[1]与A[1])  当前数组为：4,2,8,7,1,3,5,6
	 *      第二步：border=1,j=2 A[j] > A[0] 那么 border=1,                                  		       当前数组为：4,2,8,7,1,3,5,6   
	 *      第三步：border=1,j=3 A[j] > A[0] 那么 border=1,                                			       当前数组为：4,2,8,7,1,3,5,6
	 *      第四步：border=1,j=4 A[j] < A[0] 那么 border=border+1 = 2, 交换A[j]<->A[border](A[2]与A[4])  当前数组为：4,2,1,7,8,3,5,6           
	 *      第五步：border=2,j=5 A[j] < A[0] 那么 border=border+1 = 3, 交换A[j]<->A[border](A[3]与A[5])  当前数组为：4,2,1,3,8,7,5,6  
	 *      第六步：border=3,j=6 A[j] > A[0] 										     			        当前数组为：4,2,1,3,8,7,5,6  
	 *      第七步：border=3,j=7 A[j] > A[0] 										 			                     当前数组为：4,2,1,3,8,7,5,6 
	 *      第 八步:border=3,j=8; j>=A.length 交换A[border]<->A[0](A[3]与A[0])					                     当前数组为：3,2,1,4,8,7,5,6  返回 border
	 * @param array
	 * @param position
	 * @return
	 */
	static public int  partition(int [] A,int begin,int end,int position)
	{
		swap(A,begin,position);
		int povit = A[begin];
		int i = begin;
		int border = begin+1;
		for(i = begin;i <= end; i++)
		{
			if(A[i]<povit)
			{
				swap(A,border,i);
				border++;
			}		
		}
		
		swap(A,border-1,begin);
		
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
		
		//如果 pivot左边(加上pivot)恰好有k个数，则 返回pivot 因为他是第k大的
		if(ith == kth)
			return array[povitPos];
		else if(ith>kth) //如果pivot左边(加上pivot)有n个数 ，n>k对pivot左边的元素递归调用这个算法
		{
			return findNthMin(array,begin,povitPos-1,kth); 
		}
		else //如果pivot左边(加上pivot)有n个数n<k,说明现在我们知道了n个最小的值了，只需要递归第在pivot右边找第（k-n）个最小值即可
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