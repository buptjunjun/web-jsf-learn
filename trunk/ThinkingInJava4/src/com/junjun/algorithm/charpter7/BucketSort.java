package com.junjun.algorithm.charpter7;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;

/**
 * ��������������� Ͱ���� 
 */

public class BucketSort 
{
	
	//������
	class Elem
	{
		int value;
		Elem next = null;
		
		public Elem(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	/**
	 * Ͱ���� ������ʱ�������һ�֣�������Ҫ�϶�Ķ����ڴ�,������ҪһЩ��ǿ��������������Ҫ֪��������Ԫ�ض���ĳһ����Χ ����[0,k],���Ҵ������г��־��ȷֲ�
	 * Ͱ�����ǽ� ����Ԫ�ط�Ϊ������Χ���������Χ�ڽ�������Ȼ�����������
	 * ����
	 * ��������                        ���䵽Ͱ��						 ��˳���Ѽ�			
	 * 78           [0-10]    
	 * 17			[11-20]->12->17
	 * 39			[21-30]->21->23->26
	 * 26			[31-40]->39	
	 * 72     ->	[41-50]                -> 12 17 21 23 26 39 68 72 94
	 * 94			[51-60]
	 * 21			[61-70]->68
	 * 12			[71-80]->72
	 * 23			[81-90]
	 * 28			[91-100]->94
	 *   
	 * @param A        ��������
	 * @param maxElem  ����������Ԫ�ض�С�ڵ�����maxElem
	 * @param minElem  ����������Ԫ�ض����ڵ����ظ���maxElem 
	 * @param interval ��ô����ľͰ�ļ��
	 * @return
	 */
	public int [] sort(int A[],int minElem, int maxElem,int interval)
	{
		// Ͱ�ĸ���
		int num = (maxElem-minElem)/interval+1;
		
		//��ʼ������ľͰΪnull
		Elem [] buckets = new Elem[num];
		Arrays.fill(buckets, null);
		
		//��ÿһ��Ԫ�ط��䵽ÿһ��Ͱ��
		for(int i = 0; i < A.length; i++)
		{
			// ��A[i]���䵽��[(A[i]-minElem)/10]��Ͱ��
			int which = (A[i]-minElem)/10;
			Elem bucket = new Elem(A[i]) ;
			
			if(buckets[which] == null)
			{		
				buckets[which]= bucket;
			}
			else
			{
				Elem head = buckets[which];
				while(A[i] < head.getValue() && head.next != null)
				{
					head = head.next;
				}
				
				//�ҵ������head,��������head��
				Elem tmp = head.next;
				head.next = bucket;
				bucket.next = tmp;
				
			}
		}
		
		// ��˳���Ѽ�ÿһ��Ͱ
		int j = 0;
		for(int i=0; i < buckets.length; i++ )
		{
			//ȡ��һ��ľͰ�����Ѽ�����ľͰ��Ԫ��
			Elem head = buckets[i];
			while(head!=null)
			{
				A[j++] = head.getValue();
				head = head.next;
			}
		}
		
		return A;
		
	}
	
	
	public static void main(String [] args)
	{
		int [] A = { 72,26,12, 17, 21, 23, 39, 68, 94};
		BucketSort cs = new BucketSort();
		print(A, 0,A.length-1,"original array ");
		int [] B = cs.sort(A, -1,100,5);
		print(B, 0,B.length-1,"BucketSort array ");
	
		

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
