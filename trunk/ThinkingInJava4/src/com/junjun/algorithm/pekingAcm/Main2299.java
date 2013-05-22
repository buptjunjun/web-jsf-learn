package com.junjun.algorithm.pekingAcm;

import java.util.Scanner;

/**
 * ������Եĸ�����ð�����򽻻��Ĵ��������������ĺ�
 * ���÷��εİ취:
 * ��������A[p,...,q]
 * �����������Σ�
 * ��A[p,middle]���������ĸ���
 * ��A[middle+1,q]���������ĸ���
 * �ٽ�A[p,middle] �� A[middle+1,q]merge�������������������������ĸ���
 * http://poj.org/problem?id=2299
 *
 */

public class Main2299
{
	/**
	 * merge������������
	 * @param array
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static long  merge(long [] array,int x,int y,int z)
	{
		long [] tmp = new long[z-x+1];
		
		//����Եĸ���
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
				//��������Եĸ���
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
	 * ��ʵ����merge�Ĺ���
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
		 //��array[x,middle]���������ĸ���
		 long leftPairs = countInversPair(array,x,middle);
		 //��array[middle+1,y]���������ĸ���
		 long rightPairs = countInversPair(array,middle+1,y);
		 //�ٽ�array[x,middle] �� array[middle+1,y]merge�������������������������ĸ���
		 long pairs = merge(array,x,middle,y);
		 
		 //����array[x,y]���������������ĸ���
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