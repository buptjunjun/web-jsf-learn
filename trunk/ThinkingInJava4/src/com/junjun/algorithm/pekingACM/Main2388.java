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
*  ����������֣��ҵ�k���ֵ O(n)
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
	 * 	  ��һ������A[m,n] �ֽ⣬ʹ��A[pivot]����ߵ�ֵ��С��A[pivot]��A[pivot]���ұߵ�ֵ��������A[pivot]��
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
	 *  ������array[from,to]���ҵ�һ����kС��Ԫ�� 
	 * rarrayndom-select �㷨��
	 * �����ڿ��������е����ѡ��һ��pivot=p,��pivot��ߵĶ���pivotС array[from,]��pivot�ұߵĶ���pivot��
	 * ��� pivot���(����pivot)ǡ����k�������� ����pivot ��Ϊ���ǵ�k���
	 * ���pivot���(����pivot)��n���� ��n>k��pivot��ߵ�Ԫ�صݹ��������㷨
	 * ���pivot���(����pivot)��n����n<k,˵����������֪����n����С��ֵ�ˣ�ֻ��Ҫ�ݹ����pivot�ұ��ҵڣ�k-n������Сֵ����
	 * �㷨ʱ�临�Ӷ�Ϊo(n)
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
		
		//��� pivot���(����pivot)ǡ����k�������� ����pivot ��Ϊ���ǵ�k���
		if(ith == kth)
			return array[povitPos];
		else if(ith>kth) //���pivot���(����pivot)��n���� ��n>k��pivot��ߵ�Ԫ�صݹ��������㷨
		{
			return findNthMin(array,begin,povitPos-1,kth); 
		}
		else //���pivot���(����pivot)��n����n<k,˵����������֪����n����С��ֵ�ˣ�ֻ��Ҫ�ݹ����pivot�ұ��ҵڣ�k-n������Сֵ����
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