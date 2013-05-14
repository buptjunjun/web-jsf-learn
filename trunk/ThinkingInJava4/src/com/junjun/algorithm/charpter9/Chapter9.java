package com.junjun.algorithm.charpter9;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * 中位数 顺序统计学
 */

public class Chapter9 
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
	 * 找到数组A[from,to]中最大的元素
	 * 时间复杂度为o(n) 同理找到最小值也是相同的道理
	 * @return 返回其位置
	 */
	public int findMax(int []A ,int from, int to)
	{
		int max = Integer.MIN_VALUE;
		int index = from;
		for(int i = from; i <= to; i++)
		{
			if(A[i] > max)
			{
				max = A[i];
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * 同时找到最大值和最小值
	 * 如果对每一个元素我们都去与当前 最大值和最小值比较一下的话，每一个元素都要比较2次，时间为 2n
	 * 我们这样来做：先两两比较，再将较大的与当前最大的比较，较小的与当前较小的比较，再得到当前最小与最大值。
	 * 这样我们2个元素进行了3次比较 平均每一个元素比较 3/2 次,所以只需要比较3n/2 次
	 * @param args
	 * @return 返回[最大，最小]
	 */
	public int[] findMaxMin(int []A ,int from, int to)
	{
		int []  ret = {-1,-1};
		int maxIndex = from;
		int minIndex = from;
		
		int currentMin = Integer.MAX_VALUE;
		int currentMax = Integer.MIN_VALUE;
		
		//两两比较
		int i = from;
		for(i = from ; i < to; i+=2)
		{
			if(i+1 > to) //如果是奇数
				break;
			
			int smaller = Integer.MIN_VALUE;
			int bigger = Integer.MAX_VALUE;
			/*下面进行三次比较*/
			//两两比较 找到较大的
			if(A[i] <= A[i+1])
			{
				smaller = i;
				bigger = i+1;
			}
			else
			{
				smaller = i+1;
				bigger = i;
			}
			
			//较小的与当前最小值比较
			if(A[smaller] < currentMin)
			{
				currentMin = A[smaller];
				minIndex = smaller;
			}
			
			//较大与当前最大值比较
			if(A[bigger] > currentMax)
			{
				currentMax = A[bigger];
				maxIndex = bigger;
			}		
		}
		
		if(i == to) //说明是奇数个  
		{
			//最后一个元素与当前最大最小元素比较
			if (A[to] < currentMin)
				minIndex = to;
			//最后一个元素与当前最大最小元素比较
			if (A[to] > currentMax)
				maxIndex = to;
			
		}
		ret[0] = maxIndex;
		ret[1] = minIndex;
		return ret;
	}
	
	/**
	 * 在数组A[from,to]中找到一个第k小的元素 
	 * random-select 算法：
	 * 类似于快速排序中的随机选择一个pivot=p,是pivot左边的都比pivot小 A[from,]，pivot右边的都比pivot大
	 * 如果 pivot左边(加上pivot)恰好有k个数，则 返回pivot 因为他是第k大的
	 * 如果pivot左边(加上pivot)有n个数 ，n>k对pivot左边的元素递归调用这个算法
	 * 如果pivot左边(加上pivot)有n个数n<k,说明现在我们知道了n个最小的值了，只需要递归第在pivot右边找第（k-n）个最小值即可
	 * 算法时间复杂度为o(n)
	 * @param A
	 * @param from
	 * @param to
	 * @param k
	 * @return
	 */
	public int findKthMin(int []A ,int from, int to,int k)
	{
		if (from == to)
			return from;
		
		QuickSort qs = new QuickSort(); //引自快速排序
		Random r = new Random(); //随机选择[from,...,to]中的一个
		int rand = r.nextInt(to-from+1)+from;
		int pivot = qs.partition1(A, from, to, rand);	
		int left = pivot - from +1;
		if (left == k)    //如果 pivot左边(加上pivot)恰好有k个数，则 返回pivot 因为他是第k大的
			return pivot;
		else if(left > k) //如果pivot左边(加上pivot)有left个数 ，left>k对pivot左边的元素递归调用这个算法
		{
			return findKthMin(A,from,pivot-1,k);
		}
		else
		{  
			//如果pivot左边(加上pivot)有left个数left<k,说明现在我们知道了left个最小的值了，只需要递归第在pivot右边找第（k-left）个最小值即可
			//例如我们的原始的数据位2,3,1,5,6,8,10(下标从1开始,2的下标为1) 我们要找第6小的数,k=6,现在pivot=4(就是5) 由于4<6 ( 左边4个都比第k小的数小)
			//所以我们只需要在pivot的右边 即:6,8,10找第2 (就是6-4)小的数即可，右边第二小的数就是整个数组第6小的数。
			k = k - left;
			return findKthMin(A,pivot+1,to,k);
		}
		
		
		
	}
	public static void main(String [] args)
	{
		int [] A = {4,2,8,7,1,3,5,6,9090,0};
		Chapter9 ch9 = new Chapter9();
		print(A, 0,A.length-1,"original array ");
		
		//找到最大值
		int p = ch9.findMax(A,0,A.length-1);
		System.out.println("max is:"+A[p]);
		
		//同时找最大最小
		int[] ret = ch9.findMaxMin(A,0,A.length-1);
		System.out.println("max is:"+A[ret[0]]+" min is:"+A[ret[1]]);
		
		//同时找最大最小
		for(int k = 1; k<= A.length; k++)
		{
			int kthMax = ch9.findKthMin(A,0,A.length-1,k);
			System.out.println("第"+k+"小的是:"+A[kthMax]);
		}
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
