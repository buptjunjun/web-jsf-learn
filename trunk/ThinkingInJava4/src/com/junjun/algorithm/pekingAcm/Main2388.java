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
	 * �ָ�
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