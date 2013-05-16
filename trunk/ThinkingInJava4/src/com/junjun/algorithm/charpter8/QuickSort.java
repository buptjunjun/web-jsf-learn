package com.junjun.algorithm.charpter8;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;

/**
 * ��������������� 
 */

public class QuickSort 
{

	/**
	 * ����A[i] <-> A[j]
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
	 *  ��һ������A[m,n] �ֽ⣬ʹ��A[pivot]����ߵ�ֵ��С��A[pivot]��A[pivot]���ұߵ�ֵ��������A[pivot]��
	 *  ����A=[1,2,5,0,3,6] pivot=4 A[pivot] = 3.
	 *  ���ĵ��Ľ��Ϊ[1,2,0,3,5,6] ���� 3
	 *  
	 *  �㷨������A[pivot] <-> A[0]
	 *      Pivot = A[0]
	 *      i=0
	 *      for j<- 1 to A.length
	 *          if A[j] < A[0]
	 *             i++
	 *             A[j] <-> A[i]
	 *      A[i++] <-> A[0]
	 *      
	 *      ���磺
	 *      [4,2,8,7,1,3,5,6]
	 *      ���pivotΪA[0]=4
	 *      ��һ����border=0,j=1 A[j] < A[0] ��ô border=border+1 = 1, ����A[j]<->A[border](A[1]��A[1])  ��ǰ����Ϊ��4,2,8,7,1,3,5,6
	 *      �ڶ�����border=1,j=2 A[j] > A[0] ��ô border=1,                                  		       ��ǰ����Ϊ��4,2,8,7,1,3,5,6   
	 *      ��������border=1,j=3 A[j] > A[0] ��ô border=1,                                			       ��ǰ����Ϊ��4,2,8,7,1,3,5,6
	 *      ���Ĳ���border=1,j=4 A[j] < A[0] ��ô border=border+1 = 2, ����A[j]<->A[border](A[2]��A[4])  ��ǰ����Ϊ��4,2,1,7,8,3,5,6           
	 *      ���岽��border=2,j=5 A[j] < A[0] ��ô border=border+1 = 3, ����A[j]<->A[border](A[3]��A[5])  ��ǰ����Ϊ��4,2,1,3,8,7,5,6  
	 *      ��������border=3,j=6 A[j] > A[0] 										     			        ��ǰ����Ϊ��4,2,1,3,8,7,5,6  
	 *      ���߲���border=3,j=7 A[j] > A[0] 										 			                     ��ǰ����Ϊ��4,2,1,3,8,7,5,6 
	 *      �� �˲�:border=3,j=8; j>=A.length ����A[border]<->A[0](A[3]��A[0])					                     ��ǰ����Ϊ��3,2,1,4,8,7,5,6  ���� border  
	 * @param A ����
	 * @pivot ���ĵ�λ��
	 * @return pivot����λ��
	 */
	public int partition1(int [] A, int m,int n,int pivot)
	{
		swap(A,m,pivot);
		
		//border�� ����A[pivot]��С��A[pivot]�ı߽� 
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
	 *  ��һ������A[m,n] �ֽ⣬ʹ��A[pivot]����ߵ�ֵ��С��A[pivot]��A[pivot]���ұߵ�ֵ��������A[pivot]��
	 *  ����A=[1,2,5,0,3,6] pivot=4 A[pivot] = 3.
	 *  ���ĵ��Ľ��Ϊ[1,2,0,3,5,6] ���� 3
	 *  
	 *  �㷨������A[pivot] <-> A[m]
	 *  	i=m+1;j=n
	 *      1���Ӻ���ǰ���� j--,�ҵ���һ��С��A[m](pivot)��Ԫ�� Ȼ��A[m]<->A[j] m = j;j--
	 *      2����ǰ��������i++,�ҵ���һ������A[m](pivot)��Ԫ�� Ȼ��A[m]<->A[i] m = i; j--
	 *      3���ظ�1,2 ֱ�� i>=j
	 *      
	 *      ���磺
	 *      [4,2,8,7,1,3,5,6]
	 *      ���pivotΪA[0]=4 i = 1,j=7 m = 0
	 *      ��һ����  i<j �Ӻ���ǰ i=1 j=7 A[j] = 6 m=0  A[j]>=A[m] 										 	��ǰ����Ϊ��4,2,8,7,1,3,5,6
	 *      �ڶ�����  i<j �Ӻ���ǰ i=1 j=6 A[j] = 5 m=0  A[j]>=A[m] 											��ǰ����Ϊ��4,2,8,7,1,3,5,6   
	 *      ��������  i<j �Ӻ���ǰ i=1 j=5 A[j] = 3 m=0  A[j]<A[m] ����A[j]<->A[m](4��3) m=j=5 		                                     ��ǰ����Ϊ��3,2,8,7,1,4,5,6
	 *      ���Ĳ���  i<j ��ǰ���i=1 j=5 A[i] = 2 m=5   A[i]<=A[m]									 		 ��ǰ����Ϊ��3,2,8,7,1,4,5,6         
	 *      ���岽��  i<j ��ǰ��� i=2 j=5 A[i] = 8 m=5  A[i]>A[m] ����A[i]<->A[m](8��4) m=i=2  	  	           	��ǰ����Ϊ�� 3,2,4,7,1,8,5,6
	 *		��������  i<j �Ӻ���ǰ i=2 j=5 A[j] = 8 m=2  A[j]>=A[m]  								                                    ��ǰ����Ϊ��3,2,4,7,1,8,5,6
	 *      ���߲���  i<j �Ӻ���ǰ i=2 j=4 A[j] = 1 m=2  A[j]<A[m]  ����A[j]<->A[m](4��A1) m=j=4					 ��ǰ����Ϊ��3,2,1,7,4,8,5,6
	 *      �ڰ˲���  i<j ��ǰ���  i=2 j=4 A[i] = 1 m=4  A[i]<=A[m] 											��ǰ����Ϊ��3,2,1,7,4,8,5,6
	 *      �ھŲ���  i<j ��ǰ���  i=3 j=4 A[i] = 7 m=4  A[i] >A[m] ����A[j]<->A[m](7��4) m=3					��ǰ����Ϊ��3,2,1,4,7,8,5,6
	 *      ��ʮ��      i<j �Ӻ���ǰ i=3 j=4 A[j] = 7 A[j]>=A[m] 													��ǰ����Ϊ��3,2,1,4,7,8,5,6
	 *      ���         i>=j i=j=3 ����3(A[3]=4)
	 * @param A ����
	 * @pivot ���ĵ�λ��
	 * @return pivot����λ��
	 */
	public int partition2(int [] A, int m,int n,int pivot)
	{
		swap(A,m,pivot);
		
		int i = m+1; 
		int j = n;
		while(i < j)
		{
			//�Ӻ���ǰ
			while(i < j && A[j] > A[m]) j--;
			swap(A,j,m);
			m = j;
			//��ǰ���
			while(i < j && A[i] < A[m]) i++;
			swap(A,i,m);
			m=i;
		}
		return i;
	}


	/**
	 *  ��һ������A[m,n] �ֽ⣬ʹ��A[pivot]����ߵ�ֵ��С��A[pivot]��A[pivot]���ұߵ�ֵ��������A[pivot]��
	 *  ����A=[1,2,5,0,3,6] pivot=4 A[pivot] = 3.
	 *  ���ĵ��Ľ��Ϊ[1,2,0,3,5,6] ���� 3
	 * @param A ����
	 * @pivot ���ĵ�λ��
	 * @return pivot����λ��
	 */
	public int partition(int [] A, int m,int n,int pivot)
	{
		return partition1(A,m,n,pivot);
	}
	/**
	 * ��A[m...n]���п������� ѡ��A[m,n]��A[m]Ϊpivot
	 * @param A 
	 * @param m
	 * @param n
	 * @return
	 */
	public void quickSort(int [] A,int m, int n)
	{
		if(m >= n)
			return ;
		// ѡ��0Ϊpivot
		int pivot = partition(A, m,n,m);
		quickSort(A,m,pivot-1);
		quickSort(A,pivot+1,n);
	}
	
	/**
	 * ��A[m...n]���п�������  ���ѡ��ѡ��A[m,n]��A[rand]Ϊpivot
	 * �������Եõ���õ� ƽ��Ч��
	 * @param A 
	 * @param m
	 * @param n
	 * @return
	 */
	public void quickSortRandom(int [] A,int m, int n)
	{
		if(m >= n)
			return ;
		// ���ѡ��һ��pivot
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
		
		//����partition
		int pivot = qs.partition1(A, 0,A.length-1,1);
		print(A, 0,A.length-1,"����partition");
		System.out.println("pivot = " + pivot+"   A["+pivot+"] = " +A[pivot]);
	
		//���Կ������� 
		qs.quickSort(A, 0, A.length-1);
		print(A, 0,A.length-1,"���Կ������� ");
		
		// ���Կ������� ���ѡ��pivot
		// ����һ��A
		RandomArray ra = new RandomArray();
		A = ra.disturb2(A);
		print(A, 0,A.length-1,"���Һ��A");
		qs.quickSortRandom(A, 0, A.length-1);
		print(A, 0,A.length-1,"���Կ������� ���ѡ��pivot");
		

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
