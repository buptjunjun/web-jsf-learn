package com.junjun.algorithm.charpter8;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;

/**
 * 快速排序相关内容 
 */

public class QuickSort 
{

	/**
	 * 交换A[i] <-> A[j]
	 * @param A
	 * @param i
	 * @param j
	 */
	private void swap(int A [] , int i,int j)
	{
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
	
	
	/**
	 *  将一个数组A[m,n] 分解，使得A[pivot]的左边的值都小于A[pivot]，A[pivot]的右边的值都大于于A[pivot]。
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
	 * @param A 数组
	 * @pivot 轴心的位置
	 * @return pivot的新位置
	 */
	public int partition1(int [] A, int m,int n,int pivot)
	{
		swap(A,m,pivot);
		
		//border是 大与A[pivot]与小于A[pivot]的边界 
		int border = m;
		for(int j=m+1;j <=n;j++)
		{
			if(A[j] < A[m])
			{
				border++;
				swap(A,border,j);
			}
		}
		swap(A,m,border);
		return border;
	}
	
	/**
	 *  将一个数组A[m,n] 分解，使得A[pivot]的左边的值都小于A[pivot]，A[pivot]的右边的值都大于于A[pivot]。
	 *  比如A=[1,2,5,0,3,6] pivot=4 A[pivot] = 3.
	 *  最后的到的结果为[1,2,0,3,5,6] 返回 3
	 *  
	 *  算法：交换A[pivot] <-> A[m]
	 *  	i=m+1;j=n
	 *      1、从后往前搜索 j--,找到第一个小于A[m](pivot)的元素 然后A[m]<->A[j] m = j;j--
	 *      2、从前往后搜索i++,找到第一个大于A[m](pivot)的元素 然后A[m]<->A[i] m = i; j--
	 *      3、重复1,2 直到 i>=j
	 *      
	 *      例如：
	 *      [4,2,8,7,1,3,5,6]
	 *      如果pivot为A[0]=4 i = 1,j=7 m = 0
	 *      第一步：  i<j 从后向前 i=1 j=7 A[j] = 6 m=0  A[j]>=A[m] 										 	当前数组为：4,2,8,7,1,3,5,6
	 *      第二步：  i<j 从后向前 i=1 j=6 A[j] = 5 m=0  A[j]>=A[m] 											当前数组为：4,2,8,7,1,3,5,6   
	 *      第三步：  i<j 从后向前 i=1 j=5 A[j] = 3 m=0  A[j]<A[m] 交换A[j]<->A[m](4和3) m=j=5 		                                     当前数组为：3,2,8,7,1,4,5,6
	 *      第四步：  i<j 从前向后i=1 j=5 A[i] = 2 m=5   A[i]<=A[m]									 		 当前数组为：3,2,8,7,1,4,5,6         
	 *      第五步：  i<j 从前向后 i=2 j=5 A[i] = 8 m=5  A[i]>A[m] 交换A[i]<->A[m](8和4) m=i=2  	  	           	当前数组为： 3,2,4,7,1,8,5,6
	 *		第六步：  i<j 从后向前 i=2 j=5 A[j] = 8 m=2  A[j]>=A[m]  								                                    当前数组为：3,2,4,7,1,8,5,6
	 *      第七步：  i<j 从后向前 i=2 j=4 A[j] = 1 m=2  A[j]<A[m]  交换A[j]<->A[m](4和A1) m=j=4					 当前数组为：3,2,1,7,4,8,5,6
	 *      第八步：  i<j 从前向后  i=2 j=4 A[i] = 1 m=4  A[i]<=A[m] 											当前数组为：3,2,1,7,4,8,5,6
	 *      第九步：  i<j 从前向后  i=3 j=4 A[i] = 7 m=4  A[i] >A[m] 交换A[j]<->A[m](7和4) m=3					当前数组为：3,2,1,4,7,8,5,6
	 *      第十步      i<j 从后向前 i=3 j=4 A[j] = 7 A[j]>=A[m] 													当前数组为：3,2,1,4,7,8,5,6
	 *      最后         i>=j i=j=3 返回3(A[3]=4)
	 * @param A 数组
	 * @pivot 轴心的位置
	 * @return pivot的新位置
	 */
	public int partition2(int [] A, int m,int n,int pivot)
	{
		swap(A,m,pivot);
		
		int i = m+1; 
		int j = n;
		while(i < j)
		{
			//从后向前
			while(i < j && A[j] > A[m]) j--;
			swap(A,j,m);
			m = j;
			//从前向后
			while(i < j && A[i] < A[m]) i++;
			swap(A,i,m);
			m=i;
		}
		return i;
	}


	/**
	 *  将一个数组A[m,n] 分解，使得A[pivot]的左边的值都小于A[pivot]，A[pivot]的右边的值都大于于A[pivot]。
	 *  比如A=[1,2,5,0,3,6] pivot=4 A[pivot] = 3.
	 *  最后的到的结果为[1,2,0,3,5,6] 返回 3
	 * @param A 数组
	 * @pivot 轴心的位置
	 * @return pivot的新位置
	 */
	public int partition(int [] A, int m,int n,int pivot)
	{
		return partition1(A,m,n,pivot);
	}
	/**
	 * 对A[m...n]进行快速排序 选择A[m,n]中A[m]为pivot
	 * @param A 
	 * @param m
	 * @param n
	 * @return
	 */
	public void quickSort(int [] A,int m, int n)
	{
		if(m >= n)
			return ;
		// 选择0为pivot
		int pivot = partition(A, m,n,m);
		quickSort(A,m,pivot-1);
		quickSort(A,pivot+1,n);
	}
	
	/**
	 * 对A[m...n]进行快速排序  随机选择选择A[m,n]中A[rand]为pivot
	 * 这样可以得到最好的 平均效率
	 * @param A 
	 * @param m
	 * @param n
	 * @return
	 */
	public void quickSortRandom(int [] A,int m, int n)
	{
		if(m >= n)
			return ;
		// 随机选择一个pivot
		int pivotRand= new Random().nextInt(n-m) + m;
		
		int pivot = partition(A, m,n,pivotRand);
		quickSort(A,m,pivot-1);
		quickSort(A,pivot+1,n);
	}
	
	
	public static void main(String [] args)
	{
		int [] A = {4,2,8};
		QuickSort qs = new QuickSort();
		print(A, 0,A.length-1,"original array ");
		
		//测试partition
		int pivot = qs.partition1(A, 0,A.length-1,1);
		print(A, 0,A.length-1,"测试partition");
		System.out.println("pivot = " + pivot+"   A["+pivot+"] = " +A[pivot]);
	
		//测试快速排序 
		qs.quickSort(A, 0, A.length-1);
		print(A, 0,A.length-1,"测试快速排序 ");
		
		// 测试快速排序 随机选择pivot
		// 扰乱一下A
		RandomArray ra = new RandomArray();
		A = ra.disturb2(A);
		print(A, 0,A.length-1,"扰乱后的A");
		qs.quickSortRandom(A, 0, A.length-1);
		print(A, 0,A.length-1,"测试快速排序 随机选择pivot");
		

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
