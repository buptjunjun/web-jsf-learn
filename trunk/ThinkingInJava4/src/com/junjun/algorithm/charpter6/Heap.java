package com.junjun.algorithm.charpter6;
import java.util.*;
import java.util.Map.Entry;

/**
 * 堆排序相关内容 
 */

public class Heap 
{
	int [] heap = null;

	/**
	 * 将一个数组A[1,n]调整为一个大顶堆  向下调整
	 * @param A 待调整的数组
	 * @param i 欲调整的节点
	 * @param length 
	 * @return
	 */
	public int []  adjustDOWN(int A[],int i,int n)
	{
		int Ai = A[i];
		int bigger = i*2;
		while(true)
		{
			// 左子 右子的坐标
			int left = i*2; 
			int right = i*2+1;
			
			// 找出left与right中的较大的 存放在bigger中
			bigger = left;
			if(right <= n && left <= n)
				bigger = A[left] > A[right]?left:right;
			else if( right > n && left <= n )
				bigger = left;
			else // 如果left 与right 多超出了n 跳出循环
				break; 
			
			if(Ai < A[bigger])
				A[i] = A[bigger];
			else break;
			
			i = bigger;
		}
		
		if(i <= n)
			A[i] = Ai;
		return A;
	}
	
	/**
	 * 将一个数组A[1,n]调整为一个大顶堆   向上调整
	 * @param A 待调整的数组
	 * @param i 欲调整的节点
	 * @param n 最后一个叶子节点的位置
	 * @return
	 */
	public int []  adjustUP(int A[],int i,int n)
	{
		if(i > n ) 
			return A;
		
		int Ai = A[i];
		int parent = i/2;
		while(parent > 0)
		{
			//如果待调整的节点比其父节点大 交换A[i]与其父节点
			if(Ai > A[parent])
			{
				A[i] = A[parent];
				i = parent;
				parent = i/2;
			}
			else
				break;
		}
		
		if(i > 0 )
			A[i] = Ai;
		return A;
	}
	
	
	/**
	 * 将A[1...n]调整为一个大顶堆
	 * @param A
	 * @return
	 */
	public int [] adjust2BiggerHeap(int [] A,int n)
	{
		// 从第一个非叶子节点开始调整每一个节点
		for(int i = n/2+1; i >=1; i--)
		{
			adjustDOWN(A, i,n);
		}
		
		return A;
	}
	
	
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
	 * 对A[1..A.length]进行堆排序
	 * @param A
	 */
	public void heapSort(int [] A )
	{
		//先调整为大顶堆
		adjust2BiggerHeap(A,A.length-1);
		int i = A.length-1;
		while(i >= 1)
		{	
			//交换堆顶与堆尾部
			swap(A,1,i);
			i--;
			
			//调整为大顶堆
			adjustDOWN(A,1,i);
		}
	}
	
	/**
	 * A已经是一个大顶堆,现在要调整 A[i]的值，使得其依然是一个大顶堆
	 * 算法：
	 *    如果 newValue < parent[i] 比父节点小 ，向下调整  
	 *    如果  newValue >=  parent[i] 向上调整  交换父节点与A[i]直到 成为一个大顶堆
	 * @param A 存放堆的数组 A[1,A.length]
	 * @param i 要改变的位置
	 * @param 堆的边界
	 * @param newValue 要改变的值
	 */
	public int [] changeValue(int [] A, int i,int n, int newValue)
	{
		A[i] = newValue;
		this.adjustDOWN(A, i, n);
		this.adjustUP(A, i, n);
		return A;
	}
	
	/**
	 * A[1...n]已经是一个大顶堆,像一个大顶堆中插入一个新的值newValue, 要求将A调整为一个大顶堆
	 * 算法：
	 *   A[n+1] = newValue;
	 *   调用adjustUP
	 *   
	 * @param A 存放堆的数组 A[1,n]
	 * @param n 插入前 堆的边界
	 * @param newValue 要插入的值
	 */
	public int [] insertValue(int [] A, int n, int newValue)
	{	
		A[n+1] = newValue;
		this.adjustUP(A, n+1, n+1);
		return A;
	}

	public static void main(String [] args)
	{
		int [] A = {0,2,3,4,1,2,2,3,1000,10,22,44,-11,-21,Integer.MIN_VALUE};
		Heap h = new Heap();
		
		// 调整为大顶堆
		h.adjust2BiggerHeap(A,A.length-2);
		print(A, A.length-2);
		System.out.print("\n");
		
		// 调整一个元素的值
		h.changeValue(A, 5, A.length-2, 100000);
		print(A, A.length-2);
		System.out.print("\n");
		
		// 调整一个元素的值
		h.insertValue(A,A.length-2,12111);
		print(A, A.length-1);
		System.out.print("\n");
		
		//堆排序
		h.heapSort(A);
		print(A, A.length-1);
	
	}
	
	static void print(int [] A,int n)
	{
		for(int i = 1; i<=n; i++)
		{
			System.out.print(A[i]+" ");
		}
	}
}
